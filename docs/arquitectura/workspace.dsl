workspace "PFC - Gestion Bibliotecaria" "Documentacion de arquitectura C4 (Nivel 1 y 2) del sistema de gestion bibliotecaria (ejercicio de practica - Unidad III, Aplicaciones Web)." {

    model {
        lector = person "Lector" "Consulta el catalogo de libros disponibles."
        administrador = person "Administrador" "Gestiona el catalogo de libros (crear, actualizar, eliminar). Requiere autenticacion."

        sistemaPfc = softwareSystem "Sistema PFC - Gestion Bibliotecaria" "Sistema de gestion bibliotecaria (ejercicio de practica - Unidad III, Aplicaciones Web). Permite consultar y administrar el catalogo de libros." {

            frontend = container "Frontend Angular" "Interfaz web (SPA) que permite consultar el catalogo y, si el usuario esta autenticado como administrador, gestionar libros." "Angular 17+" "Frontend"

            backend = container "Backend Spring Boot" "Expone la API REST del catalogo de libros y la autenticacion, aplicando las reglas de negocio (capas Controller-Service-Repository-Entity)." "Java 21 / Spring Boot 3.5.x" "Backend"

            baseDatos = container "Base de datos PostgreSQL" "Almacena de forma persistente el catalogo de libros y los usuarios del sistema." "PostgreSQL 16" "Database"

            redis = container "Redis" "Cumple doble funcion: (1) cache-aside de las consultas de listado de libros para reducir la carga sobre PostgreSQL, y (2) blacklist de JTI de tokens JWT para permitir logout efectivo en un esquema de autenticacion stateless." "Redis 7" "Cache"
        }

        lector -> frontend "Consulta el catalogo de libros usando" "HTTPS"
        administrador -> frontend "Administra el catalogo de libros usando" "HTTPS"

        frontend -> backend "Realiza llamadas a la API REST (incluye login/logout)" "HTTPS/REST (JSON)"
        backend -> baseDatos "Lee y escribe libros y usuarios" "JDBC"
        backend -> redis "Lee y escribe entradas de cache y blacklist de JTI" "RESP (Redis protocol)"
    }

    views {
        systemContext sistemaPfc "Nivel1-Contexto" {
            include *
            autoLayout
            title "Nivel 1 - Contexto del Sistema PFC"
            description "Actores Lector y Administrador interactuando con el Sistema PFC de Gestion Bibliotecaria."
        }

        container sistemaPfc "Nivel2-Contenedores" {
            include *
            autoLayout
            title "Nivel 2 - Contenedores del Sistema PFC"
            description "Descomposicion del Sistema PFC en sus contenedores: Frontend Angular, Backend Spring Boot, PostgreSQL y Redis."
        }

        styles {
            element "Person" {
                shape person
                background #08427b
                color #ffffff
            }
            element "Software System" {
                background #1168bd
                color #ffffff
            }
            element "Frontend" {
                background #85bbf0
                color #000000
            }
            element "Backend" {
                background #438dd5
                color #ffffff
            }
            element "Database" {
                shape cylinder
                background #438dd5
                color #ffffff
            }
            element "Cache" {
                shape cylinder
                background #f5a623
                color #000000
            }
        }
    }

}
