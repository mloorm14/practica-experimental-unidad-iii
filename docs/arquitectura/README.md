# Arquitectura - Modelo C4

Este directorio contiene el modelo C4 (Nivel 1: Contexto, Nivel 2: Contenedores) del backend del PFC, definido en Structurizr DSL.

## Archivos

- `workspace.dsl` - fuente del modelo (Structurizr DSL, texto plano).
- `c4-nivel1-contexto.png` - diagrama de contexto exportado (actores + sistema PFC).
- `c4-nivel2-contenedores.png` - diagrama de contenedores exportado (Frontend Angular, Backend Spring Boot, PostgreSQL, Redis).

## Nota sobre las herramientas de Structurizr

`structurizr/lite` y `structurizr/cli` (las imagenes Docker "clasicas") estan **deprecadas**: al día de hoy solo imprimen un aviso de migración y no ejecutan ninguna acción real. El reemplazo (`structurizr/structurizr local`) sí funciona, pero corre un servidor web con **autosave activado** que ignora `workspace.dsl` si ya existe un workspace previo en el volumen montado y puede sobreescribirlo con datos de ejemplo — no se uso por ese riesgo.

Lo que sí funciona y es lo que se usó para generar los PNG de este directorio:

1. **`structurizr-cli` standalone** (jar de GitHub Releases, no la imagen Docker deprecada) para validar la sintaxis del DSL y exportar cada vista a Mermaid:
   ```bash
   structurizr.sh validate -workspace workspace.dsl
   structurizr.sh export -workspace workspace.dsl -format mermaid -output ./export
   ```
2. **`@mermaid-js/mermaid-cli`** (via `npx`) para rasterizar cada `.mmd` a PNG:
   ```bash
   npx @mermaid-js/mermaid-cli -i export/structurizr-Nivel1-Contexto.mmd -o c4-nivel1-contexto.png -b white
   npx @mermaid-js/mermaid-cli -i export/structurizr-Nivel2-Contenedores.mmd -o c4-nivel2-contenedores.png -b white
   ```

## Como regenerar los PNG despues de editar `workspace.dsl`

Repite los dos pasos de arriba. Requiere Java (para `structurizr-cli`) y Node.js (para `mermaid-cli`), ambos ya presentes en el entorno de desarrollo del equipo.

## Alternativa interactiva (opcional)

Si prefieres editar/inspeccionar el modelo visualmente en el navegador en vez de por linea de comandos, puedes levantar la herramienta nueva de Structurizr apuntando a una carpeta vacia (no la de este repo, para evitar el riesgo de autosave mencionado arriba) y pegar el contenido de `workspace.dsl` manualmente desde la UI:

```bash
docker run -it --rm -p 8080:8080 -v <carpeta-vacia>:/usr/local/structurizr structurizr/structurizr local
```

Luego abre `http://localhost:8080`.
