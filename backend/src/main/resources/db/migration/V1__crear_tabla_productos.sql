CREATE TABLE productos (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(150)    NOT NULL,
    descripcion VARCHAR(500),
    sku         VARCHAR(50)     NOT NULL,
    precio      NUMERIC(12, 2)  NOT NULL,
    stock       INTEGER         NOT NULL DEFAULT 0,
    categoria   VARCHAR(100),
    created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_productos_sku UNIQUE (sku)
);

CREATE INDEX idx_productos_categoria ON productos (categoria);
