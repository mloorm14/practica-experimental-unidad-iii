-- Alinea el dominio generico de "Producto" (inventario) al dominio real del PFC:
-- Sistema de Gestion Bibliotecaria. Todo via ALTER TABLE: no se borra ni recrea
-- la tabla, se preserva la data existente. Las columnas nuevas quedan nullable
-- porque las filas ya existentes no tienen esos valores todavia.

ALTER TABLE productos RENAME TO libros;

ALTER TABLE libros RENAME COLUMN nombre TO titulo;
ALTER TABLE libros RENAME COLUMN sku TO isbn;
ALTER TABLE libros RENAME COLUMN categoria TO genero;

-- "precio" no existe en el dominio real de Libro (no es un catalogo de venta): se elimina.
ALTER TABLE libros DROP COLUMN precio;

ALTER TABLE libros ADD COLUMN autor VARCHAR(150);
ALTER TABLE libros ADD COLUMN anio_publicacion INTEGER;
ALTER TABLE libros ADD COLUMN editorial VARCHAR(150);
ALTER TABLE libros ADD COLUMN idioma VARCHAR(50);
ALTER TABLE libros ADD COLUMN estado VARCHAR(30);

ALTER TABLE libros RENAME CONSTRAINT uk_productos_sku TO uk_libros_isbn;
ALTER INDEX idx_productos_categoria RENAME TO idx_libros_genero;
