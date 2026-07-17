-- Tablas maestras/catálogo del dominio bibliotecario.
-- Cada tabla almacena los valores permitidos para un campo categórico
-- que antes era texto libre (VARCHAR) en la tabla libros.

-- ============================================================
-- 1. Editoriales
-- ============================================================
CREATE TABLE editoriales (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(150) NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_editoriales_nombre UNIQUE (nombre)
);

-- ============================================================
-- 2. Idiomas
-- ============================================================
CREATE TABLE idiomas (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(50) NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_idiomas_nombre UNIQUE (nombre)
);

-- ============================================================
-- 3. Estados de libro
-- ============================================================
CREATE TABLE estados_libro (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(30) NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_estados_libro_nombre UNIQUE (nombre)
);
