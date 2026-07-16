# Guía de continuidad del equipo

Este documento está dirigido a cualquier persona (o IA asistente, como Claude Code) que se incorpore a este repositorio sin haber estado en las conversaciones previas del equipo. Es autocontenido: no necesitas contexto externo para entender el estado del proyecto y qué falta por hacer.

## 1. Resumen del proyecto

Este es un PFC (Proyecto de Fin de Curso) universitario: un sistema de **gestión de inventario** (catálogo de productos) con autenticación.

Stack: **Java 21 + Spring Boot 3.5.x** (backend), **Angular 17+** (frontend), **PostgreSQL 16** (base de datos relacional), **Redis 7** (cache + blacklist de JWT).

El proyecto se basa en dos documentos fuente: una guía de práctica general del curso y un enunciado de tarea puntual, que tienen algunas diferencias entre sí (por ejemplo, la guía sugiere MySQL y el enunciado exige PostgreSQL). Todas esas diferencias ya fueron detectadas y resueltas — el detalle completo, incoherencia por incoherencia, está en [`docs/handoff/DECISIONES-GUIA-VS-ENUNCIADO.md`](./DECISIONES-GUIA-VS-ENUNCIADO.md). No lo repetimos aquí; consúltalo si algo en el código no parece coincidir con la guía general del curso.

## 2. Cómo levantar el proyecto localmente desde cero

```bash
git clone https://github.com/mloorm14/practica-experimental-unidad-iii.git
cd practica-experimental-unidad-iii
cp .env.example .env
```

Abre `.env` y define tu propio `JWT_SECRET` (no reutilices el de otro entorno):

```bash
openssl rand -base64 48
```

Pega el resultado como valor de `JWT_SECRET` en `.env`. Los demás valores por defecto de `.env.example` sirven tal cual para desarrollo local.

Levanta la infraestructura (Postgres + Redis):

```bash
docker compose up -d
docker compose ps   # confirma que ambos servicios queden "healthy"
```

Arranca el backend (usa el Maven Wrapper, no necesitas Maven instalado globalmente):

```bash
cd backend
./mvnw spring-boot:run      # Windows: mvnw.cmd spring-boot:run
```

El backend queda escuchando en `http://localhost:8080`. Flyway aplica las migraciones automáticamente al arrancar (no hay que correr nada manual).

**Usuario de prueba** (creado por la migración `V2__crear_tabla_usuarios.sql`):

- `username`: `admin`
- `password`: `Admin123!`

Prueba rápida de login:

```bash
curl -i -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin123!"}'
```

La respuesta trae un `Set-Cookie: access_token=...` (HttpOnly). Guarda esa cookie para llamar a cualquier endpoint bajo `/api/**` que no sea `/api/auth/**`.

Cuando termines de trabajar, baja los contenedores para no dejarlos corriendo de fondo:

```bash
docker compose down
```

## 3. Qué está HECHO y verificado

Todo lo siguiente está implementado, compilado y probado end-to-end con evidencia real (no solo "debería funcionar"):

- **Setup del repo**: estructura de carpetas, `docker-compose.yml` (Postgres 16 + Redis 7 con healthchecks), `.env`/`.env.example`, `.gitignore`.
- **CRUD completo de `Producto`** en las 4 capas (`Controller` → `Service` → `Repository` → `Entity`), con DTOs separados de la entidad, paginación vía `Pageable`, y manejo de errores centralizado (`GlobalExceptionHandler`: 404 no encontrado, 400 validación).
- **Cache-aside con Redis** sobre `GET /api/productos` (`@Cacheable`/`@CacheEvict`, TTL 5 minutos). Verificado con `redis-cli KEYS *`: la key aparece tras el primer GET y desaparece tras cualquier escritura (evict).
- **Autenticación JWT en cookie HttpOnly con blacklist de JTI en Redis**. Login/logout funcional y verificado: login devuelve cookie, logout agrega el `jti` a la blacklist con TTL igual al tiempo restante del token, y reutilizar la cookie después del logout devuelve **401** (la prueba central de que el logout invalida el token de verdad).
- **Diagramas C4 nivel 1 (contexto) y nivel 2 (contenedores)** en Structurizr DSL, exportados a PNG (`docs/arquitectura/`).
- **3 ADR** (Architecture Decision Records) en `docs/adr/`: arquitectura en capas, Angular vs. React, Redis para cache + blacklist JWT.

## 4. Qué falta y quién lo haría

### Compañero 1: resto de entidades del dominio + datos semilla

- Replicar el patrón de `Producto` (**las mismas 4 capas**: `Entity`, `Repository`, `Service`/`ServiceImpl`, `Controller`, más sus `DTO`s de request/response) para el resto de entidades del dominio de inventario. Usa `backend/src/main/java/ec/edu/uteq/pfcbackend/{entity,repository,service,controller,dto}/Producto*` como plantilla literal — mismo estilo de nombres en español, mismo manejo de excepciones (`ResourceNotFoundException`/`BusinessException`), misma migración Flyway por entidad.
- Añadir paginación, filtros y ordenamiento a los listados nuevos, siguiendo el mismo enfoque que ya usa `ProductoRepository`/`ProductoController` (parámetro `Pageable`, métodos de query derivados de Spring Data).
- Crear el **seeder de datos semilla con 50 registros** como migración Flyway nueva: `backend/src/main/resources/db/migration/V3__datos_semilla.sql` (el número de versión sigue la secuencia: V1 productos, V2 usuarios, V3 es la siguiente disponible).

### Compañero 2: frontend Angular + testing + benchmark de cache

- Construir el frontend en Angular 17+ consumiendo esta API REST. **Punto crítico**: la API ya está protegida con JWT — todo lo que esté bajo `/api/**` excepto `/api/auth/**` requiere estar autenticado (cookie `access_token` válida). El frontend necesita implementar el flujo de login primero (`POST /api/auth/login`) antes de poder consumir cualquier otro endpoint; sin eso, cualquier llamada a `/api/productos` (o a las entidades nuevas del Compañero 1) devuelve 401. La cookie es HttpOnly, así que el navegador la maneja solo — Angular no necesita leer ni guardar el token manualmente, solo asegurarse de que las peticiones HTTP incluyan credenciales (`withCredentials: true` en `HttpClient`).
- Escribir tests JUnit del `Repository` con cobertura ≥70%.
- Ejecutar el benchmark de cache: 10 repeticiones de la consulta principal (`GET /api/productos`) con y sin cache, calcular el speedup `S = Tsin/Tcon`.

## 5. Convenciones de commit

- Mensajes descriptivos, en español, en modo imperativo (`"agrega X"`, `"corrige Y"`), como ya se viene haciendo en el historial.
- **Sin trailers de coautoría de IA ni menciones de herramientas de IA**, ni en el mensaje del commit ni en el autor (`git config user.name`/`user.email` debe ser siempre una persona real del equipo). El equipo prefiere que el historial de commits refleje autoría humana real — si usas un asistente de IA para escribir código, el commit lo firmas tú, no la herramienta.

## 6. Convención de ramas (sugerida, no impuesta)

El equipo puede decidir libremente entre trabajar directo sobre `main` o usar Pull Requests. Como referencia, el Anexo A de la guía menciona el patrón `feature/nombre-corto` por persona/tarea — por ejemplo `feature/data-layer` para el trabajo del Compañero 1. Es solo una sugerencia de nomenclatura, no una obligación del equipo.

## 7. Puntos de atención técnica (para no romper cosas)

- **Serialización de `Page`/Jackson en el cache**: `PageImpl` de Spring Data (y sus campos internos `Pageable`/`Sort`) no tienen constructores compatibles con Jackson, así que no se pueden cachear directamente en Redis con `GenericJackson2JsonRedisSerializer` — falla al deserializar. La solución ya implementada es `backend/src/main/java/ec/edu/uteq/pfcbackend/config/CacheablePage.java`, una subclase de `PageImpl` con constructor `@JsonCreator` que oculta `getPageable()`/`getSort()` de la serialización. **Si vas a cachear otro listado paginado nuevo (por ejemplo, de una entidad del Compañero 1), reutiliza `CacheablePage` en vez de devolver un `Page` construido con `.map()` directamente** — si no, vas a reintroducir el mismo error de deserialización.
- **Puerto de Postgres**: el contenedor expone `5433` en el host (no el `5432` estándar) porque en la máquina de desarrollo original ya había otro proyecto usando `5432`. Puertas adentro del contenedor sigue siendo `5432`; solo el mapeo externo cambia (ver `POSTGRES_PORT` en `.env` y `docker-compose.yml`). Si tu máquina no tiene ese conflicto, igual usa `5433` para mantener consistencia con el resto del equipo.
- **TTL de cache**: 5 minutos por defecto (`RedisCacheConfig`). Es intencional y configurable, pero si vas a medir el benchmark de speedup, ten en cuenta que el cache expira solo a los 5 minutos — no hace falta invalidarlo manualmente entre repeticiones si las 10 corridas se hacen en menos de ese tiempo.
- **`ddl-auto: validate`**: el esquema de base de datos se gestiona **exclusivamente por Flyway**, nunca por Hibernate. Si necesitas cambiar una tabla (agregar columna, cambiar tipo, etc.), creas una migración Flyway nueva (`V<n>__descripcion.sql`); **nunca** modifiques una entidad esperando que Hibernate actualice el esquema solo — con `validate`, Hibernate falla al arrancar si la entidad y la tabla no coinciden, en vez de alterarla silenciosamente. Esto es deliberado: evita que el esquema real diverja de lo que está versionado en `db/migration/`.
