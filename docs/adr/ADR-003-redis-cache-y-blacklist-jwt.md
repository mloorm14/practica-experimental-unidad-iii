# ADR-003: Redis para cache-aside y blacklist de JTI (JWT)

## Estado

Aceptado

## Contexto

El backend tiene dos necesidades de estado rápido y volátil que no son responsabilidad natural de PostgreSQL:

1. **Cache de lectura**: el listado paginado de libros (`GET /api/libros`) es la consulta principal del PFC y, como todo catálogo, tiene una proporción de lecturas mucho mayor que de escrituras (crear/actualizar/eliminar un libro es comparativamente infrecuente frente a listarlos). Eso hace del **cache-aside** el patrón correcto frente a read-through/write-through/write-behind: la aplicación controla explícitamente cuándo lee del cache y cuándo lo invalida (`@Cacheable`/`@CacheEvict` con `allEntries=true` en `crear`/`actualizar`/`eliminar`), sin necesitar que cada escritura pase obligatoriamente por el cache (write-through) ni asumir el riesgo de pérdida de datos de un buffer asíncrono (write-behind), que no se justifica para este volumen de escritura.

2. **Invalidación de JWT en logout**: con autenticación stateless, un JWT firmado no se puede revocar por diseño. Implementamos una blacklist de `jti` (verificado con evidencia real: login emite cookie con JWT, logout agrega el `jti` a Redis con TTL igual al tiempo restante del token, y la reutilización de la cookie post-logout efectivamente devuelve 401).

**Redis vs. Memcached**: elegimos Redis por sus estructuras de datos más ricas (aunque hoy solo usamos `String` con TTL, tener `Hash`/`Set`/`Sorted Set` disponibles deja margen para evolucionar el cache sin cambiar de motor) y por soportar persistencia opcional (RDB/AOF), que Memcached no ofrece — relevante si en el futuro se cachea algo que no sea trivialmente reconstruible.

**Redis vs. tabla de tokens revocados en PostgreSQL**: descartamos una tabla relacional para la blacklist porque Redis tiene **TTL nativo por key** (`SET key value EX segundos`): cada entrada de blacklist se autoelimina exactamente cuando el token expiraría de todas formas, sin trabajo adicional. Una tabla en Postgres exigiría un job de limpieza (cron o `@Scheduled`) para borrar filas de tokens ya expirados, que es infraestructura extra sin beneficio: la información no necesita persistir más allá de la vida del token.

## Decisión

Redis 7 cumple ambas funciones dentro del mismo backend: cache-aside (`RedisCacheManager`, TTL 5 min) y blacklist de `jti` (`StringRedisTemplate`, TTL = tiempo restante del token), como namespaces de key distintos (`libros_listado::*` vs `jwt_blacklist:*`) sobre la misma instancia.

## Consecuencias

**Positivas:** una sola pieza de infraestructura para dos responsabilidades, TTL nativo elimina limpieza manual, `GenericJackson2JsonRedisSerializer` deja el contenido inspeccionable en Redis Commander/`redis-cli` para debugging.

**Negativas:** ambas funciones comparten el mismo Redis, así que un fallo o saturación de memoria de Redis afecta simultáneamente el cache y la autenticación (un solo punto de fallo); no hay separación de recursos entre una función y otra dentro de la misma instancia.

## Riesgo de cache stampede (honesto, sin mitigar en el alcance actual)

Existe riesgo real de stampede en dos escenarios, ninguno mitigado hoy: (1) expiración por TTL de una key muy solicitada simultáneamente por varias requests, y (2) el `allEntries=true` en cada escritura vacía **todo** el cache de listados de golpe, exponiendo la siguiente ráfaga de lecturas a Postgres sin cache. No se implementó `@Cacheable(sync=true)` ni invalidación selectiva por página porque, para el volumen de tráfico esperado de un PFC académico (sin usuarios concurrentes reales en producción), el costo de mitigarlo no se justifica frente al de aceptarlo documentado.

## Alternativas consideradas

- **Memcached**: descartado por carecer de estructuras de datos ricas y de persistencia opcional; hubiera cubierto el cache-aside pero no aportaba nada adicional frente a Redis para justificar tener dos motores o renunciar a la flexibilidad futura.
- **Tabla de tokens revocados en PostgreSQL con limpieza por cron**: descartada porque exige infraestructura de limpieza (job programado) que Redis resuelve de forma nativa con TTL por key, sin código ni proceso adicional.

## Nota de contexto

Este ejercicio usa el dominio de gestión bibliotecaria como caso de práctica de la Unidad III de Aplicaciones Web, independiente del repositorio del Proyecto Fin de Curso real del autor.
