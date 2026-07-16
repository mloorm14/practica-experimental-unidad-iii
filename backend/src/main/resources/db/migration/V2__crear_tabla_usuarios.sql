CREATE TABLE usuarios (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(50)     NOT NULL,
    password    VARCHAR(255)    NOT NULL,
    rol         VARCHAR(30)     NOT NULL DEFAULT 'USER',
    created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_usuarios_username UNIQUE (username)
);

-- Usuario de prueba: username=admin / password=Admin123! (ver documentacion del equipo)
INSERT INTO usuarios (username, password, rol)
VALUES ('admin', '$2a$10$8qKhutu0CTiK5dr3egdI/O/vzGM8TfrCtV2EHaVpyNJKulZAtFC.a', 'ADMIN');
