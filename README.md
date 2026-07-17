# Practica Experimental - Unidad III (PFC)

Proyecto de Fin de Curso (PFC) grupal desarrollado como parte de la Unidad III de la asignatura.

## Stack tecnológico

- **Backend:** Java 21 + Spring Boot 3.x
- **Frontend:** Angular 17+
- **Base de datos:** PostgreSQL 16
- **Cache:** Redis 7
- **Infraestructura local:** Docker Compose

## Integrantes del equipo

Equipo D

- Castro Espinoza Kevin Moisés - Frontend Angular, tests JUnit, benchmark de cache
- Escudero Plaza María del Rosario - Backend: entidades maestras, migraciones Flyway, relaciones ORM y datos semilla.
- Loor Medranda Marlon Taylor - Backend base, arquitectura, autenticación, cache.

## Estado del proyecto

Para el estado actual del proyecto (qué está hecho, qué falta y quién lo haría, cómo levantar el entorno, y puntos de atención técnica), ver la guía de continuidad del equipo: [docs/handoff/GUIA-EQUIPO.md](docs/handoff/GUIA-EQUIPO.md).

## Estructura del proyecto

```
.
├── backend/            # API Spring Boot (Java 21)
├── frontend/           # Aplicación Angular
├── docs/
│   ├── adr/            # Architecture Decision Records
│   ├── arquitectura/   # Diagramas y documentación de arquitectura
│   └── handoff/        # Guías de continuidad para el equipo
├── docker-compose.yml  # Servicios locales: PostgreSQL + Redis
├── .env.example        # Plantilla de variables de entorno
└── .env                # Variables de entorno locales (no versionado)
```

## Puesta en marcha del entorno local

1. Copiar `.env.example` a `.env` y ajustar los valores si es necesario.
2. Levantar los servicios de infraestructura:

   ```bash
   docker compose up -d
   ```

3. Verificar que los contenedores estén saludables:

   ```bash
   docker compose ps
   ```

4. Para detener los servicios:

   ```bash
   docker compose down
   ```
