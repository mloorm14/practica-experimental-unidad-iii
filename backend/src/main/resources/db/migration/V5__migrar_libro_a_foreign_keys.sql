-- Convierte las columnas de texto libre (editorial, idioma, estado) de la tabla libros
-- en foreign keys reales hacia las tablas maestras creadas en V4.
-- Se preservan los datos existentes mapeando valores de texto a ids de las tablas maestras.

-- ============================================================
-- 1. Crear columnas nuevas (nullable temporalmente)
-- ============================================================
ALTER TABLE libros ADD COLUMN editorial_id BIGINT;
ALTER TABLE libros ADD COLUMN idioma_id    BIGINT;
ALTER TABLE libros ADD COLUMN estado_id    BIGINT;

-- ============================================================
-- 2. Mapear datos existentes de texto a ids de tablas maestras
--    (solo filas que tengan valor no nulo en la columna de texto)
-- ============================================================

-- Insertar editoriales que no existan en la tabla maestra y mapear
DO $$
DECLARE
    texto VARCHAR;
BEGIN
    FOR texto IN SELECT DISTINCT editorial FROM libros WHERE editorial IS NOT NULL AND editorial != '' LOOP
        INSERT INTO editoriales (nombre) VALUES (texto)
        ON CONFLICT (nombre) DO NOTHING;
    END LOOP;
END $$;

DO $$
DECLARE
    texto VARCHAR;
BEGIN
    FOR texto IN SELECT DISTINCT idioma FROM libros WHERE idioma IS NOT NULL AND idioma != '' LOOP
        INSERT INTO idiomas (nombre) VALUES (texto)
        ON CONFLICT (nombre) DO NOTHING;
    END LOOP;
END $$;

DO $$
DECLARE
    texto VARCHAR;
BEGIN
    FOR texto IN SELECT DISTINCT estado FROM libros WHERE estado IS NOT NULL AND estado != '' LOOP
        INSERT INTO estados_libro (nombre) VALUES (texto)
        ON CONFLICT (nombre) DO NOTHING;
    END LOOP;
END $$;

-- ============================================================
-- 3. Actualizar las columnas nuevas con los ids correspondientes
-- ============================================================
UPDATE libros SET editorial_id = e.id
    FROM editoriales e WHERE LOWER(libros.editorial) = LOWER(e.nombre)
    AND libros.editorial IS NOT NULL AND libros.editorial != '';

UPDATE libros SET idioma_id = i.id
    FROM idiomas i WHERE LOWER(libros.idioma) = LOWER(i.nombre)
    AND libros.idioma IS NOT NULL AND libros.idioma != '';

UPDATE libros SET estado_id = es.id
    FROM estados_libro es WHERE LOWER(libros.estado) = LOWER(es.nombre)
    AND libros.estado IS NOT NULL AND libros.estado != '';

-- ============================================================
-- 4. Eliminar columnas de texto libre
-- ============================================================
ALTER TABLE libros DROP COLUMN editorial;
ALTER TABLE libros DROP COLUMN idioma;
ALTER TABLE libros DROP COLUMN estado;

-- ============================================================
-- 5. Restringir las columnas nuevas a NOT NULL y agregar FK
-- ============================================================
ALTER TABLE libros ALTER COLUMN editorial_id SET NOT NULL;
ALTER TABLE libros ALTER COLUMN idioma_id    SET NOT NULL;
ALTER TABLE libros ALTER COLUMN estado_id    SET NOT NULL;

ALTER TABLE libros
    ADD CONSTRAINT fk_libros_editorial FOREIGN KEY (editorial_id) REFERENCES editoriales (id);
ALTER TABLE libros
    ADD CONSTRAINT fk_libros_idioma FOREIGN KEY (idioma_id) REFERENCES idiomas (id);
ALTER TABLE libros
    ADD CONSTRAINT fk_libros_estado FOREIGN KEY (estado_id) REFERENCES estados_libro (id);

-- ============================================================
-- 6. Indices para búsquedas por foreign key
-- ============================================================
CREATE INDEX idx_libros_editorial_id ON libros (editorial_id);
CREATE INDEX idx_libros_idioma_id ON libros (idioma_id);
CREATE INDEX idx_libros_estado_id ON libros (estado_id);
