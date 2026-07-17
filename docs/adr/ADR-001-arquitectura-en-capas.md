# ADR-001: Arquitectura monolítica en capas (Controller-Service-Repository-Entity)

## Estado

Aceptado

## Contexto

El backend del PFC lo desarrolla un equipo de 3 personas dentro de un semestre académico, con entregas parciales y un plazo fijo no negociable. Necesitábamos decidir la arquitectura base del backend antes de escribir la primera línea de código de negocio.

Dos criterios pesaron más que cualquier otro en esta decisión:

**Carga cognitiva del equipo (team cognitive load).** Un equipo de 3 personas tiene capacidad limitada para sostener complejidad operativa simultánea: control de versiones, base de datos, cache, autenticación y, si hubiera microservicios, además service discovery, comunicación entre servicios, tracing distribuido y despliegue independiente por servicio. Repartir esa carga entre microservicios no reduce el trabajo total, lo multiplica en superficie de coordinación (contratos entre servicios, versionado de APIs internas, consistencia eventual) sin que el equipo tenga el tamaño para paralelizar ese trabajo de forma efectiva.

**Falacias de los sistemas distribuidos (Peter Deutsch).** Microservicios asumen que la red es confiable, la latencia es cero, el ancho de banda es infinito y la topología no cambia — ninguna de esas suposiciones es gratis, y cada una que se viola introduce un modo de fallo nuevo (timeouts, reintentos, circuit breakers, sagas para transacciones distribuidas) que hay que diseñar, implementar y testear. Para un catálogo de libros con CRUD y autenticación, el dominio no tiene fronteras naturales que justifiquen pagar ese costo: no hay equipos distintos dueños de subdominios distintos, ni necesidad de escalar partes del sistema de forma independiente.

## Decisión

Adoptamos una arquitectura monolítica en capas: `Controller` (REST, validación de entrada) → `Service` (lógica de negocio, transacciones, cache) → `Repository` (Spring Data JPA) → `Entity` (mapeo JPA/Hibernate), con `DTO` separando el contrato HTTP del modelo de persistencia y `exception`/`config` como paquetes transversales. Un único deployable, una única base de datos.

## Consecuencias

**Positivas:** ciclo de desarrollo simple (un solo proyecto Maven, un solo `mvn spring-boot:run`), transacciones ACID nativas de Postgres sin necesidad de sagas, debugging con stack traces locales completos, curva de entrada baja para los 3 integrantes.

**Negativas:** escalado es vertical o por réplica completa del monolito, no por componente; un cambio en `entity/Libro` puede forzar recompilar y redesplegar todo el sistema aunque solo afecte una capa; si el proyecto creciera más allá del alcance académico, el acoplamiento entre módulos de negocio dentro del mismo código podría volverse un problema de mantenibilidad a largo plazo.

## Alternativas consideradas

- **Microservicios**: descartado por el costo de coordinación (service discovery, comunicación de red entre servicios, consistencia eventual) desproporcionado frente al tamaño del equipo (3 personas) y del dominio (un catálogo de libros). Hubiera introducido las falacias de sistemas distribuidos sin ningún beneficio real de escalabilidad o de organización por equipos independientes.
- **Arquitectura hexagonal / puertos y adaptadores**: se consideró brevemente por el desacoplamiento que ofrece entre dominio e infraestructura (JPA, Redis, HTTP quedarían detrás de puertos), pero se descartó por el sobrecosto de abstracción (interfaces + adaptadores adicionales) que no se justifica para un proyecto académico de este tamaño y plazo; el patrón en capas ya da separación suficiente entre HTTP, negocio y persistencia para los fines de la práctica.

## Nota de contexto

Este ejercicio usa el dominio de gestión bibliotecaria como caso de práctica de la Unidad III de Aplicaciones Web, independiente del repositorio del Proyecto Fin de Curso real del autor.
