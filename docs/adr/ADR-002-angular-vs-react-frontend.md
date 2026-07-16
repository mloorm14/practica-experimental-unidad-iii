# ADR-002: Angular 17+ para el frontend

## Estado

Aceptado

## Contexto

A diferencia de las demás decisiones técnicas de este proyecto, la tecnología de frontend **no fue una elección libre del equipo**: el enunciado de la tarea académica exige explícitamente Angular 17+. Documentamos esto con transparencia porque es parte real del contexto de la decisión — no tendría sentido fingir que evaluamos Angular contra alternativas en igualdad de condiciones cuando una de ellas venía impuesta por la consigna. Aun así, vale argumentar sus méritos técnicos objetivos frente a React, la alternativa más comparable, porque entender por qué Angular es una elección razonable (no solo obligatoria) ayuda al equipo a aprovechar mejor el framework en vez de pelear contra él.

Comparado con React, Angular difiere en varios ejes relevantes para un equipo académico de 3 personas sin experiencia previa profunda en frontend:

- **Curva de aprendizaje**: Angular tiene una curva más pronunciada al inicio (módulos, inyección de dependencias, RxJS, decoradores), pero esa estructura explícita reduce las decisiones de diseño que el equipo tendría que tomar por su cuenta.
- **Framework opinionado vs. biblioteca**: Angular es un framework completo — trae router, cliente HTTP (`HttpClient`) y manejo de formularios (`Reactive Forms`) integrados y mantenidos por el mismo equipo. React es una biblioteca de UI; para armar una SPA equivalente hay que elegir y ensamblar router, cliente HTTP y manejo de formularios de terceros, lo cual añade decisiones y superficie de configuración que un equipo con plazo fijo no necesita.
- **TypeScript**: en Angular, TypeScript es de primera clase (el CLI genera todo en TS por defecto, el framework está escrito en TS). En React, TypeScript es opcional y se adopta por convención de la comunidad, no por diseño del framework.
- **Comunidad y ecosistema**: React tiene un ecosistema más grande en número de paquetes de terceros, pero Angular tiene una comunidad grande y estable, respaldada directamente por Google, con documentación oficial más centralizada.

## Decisión

Usamos Angular 17+ para el frontend, consumiendo la API REST del backend (incluyendo el flujo de login/logout con cookie HttpOnly).

## Consecuencias

**Positivas:** estructura de proyecto uniforme desde el `angular.json`/CLI, menos decisiones de "qué librería usar para X" que resolver como equipo, TypeScript reforzado end-to-end (backend en Java tipado, frontend en TS tipado), curva de aprendizaje guiada por la documentación oficial de Angular.

**Negativas:** mayor verbosidad inicial que React para componentes simples; RxJS (usado internamente por `HttpClient` y el router) tiene su propia curva de aprendizaje que el equipo debe asumir sin experiencia previa; al ser una decisión impuesta, el equipo no evaluó exhaustivamente si el dominio del proyecto (un catálogo de productos con CRUD) realmente necesita la robustez de un framework completo frente a una solución más liviana.

## Alternativas consideradas

- **React**: descartada porque el enunciado exige Angular, pero técnicamente hubiera requerido ensamblar manualmente router (React Router), cliente HTTP (Axios/fetch) y manejo de formularios (Formik/React Hook Form), con más decisiones de integración para un equipo sin experiencia previa consolidada en frontend.
- **Vue**: no evaluada en profundidad por la misma razón (restricción del enunciado); en términos generales ofrece una curva de aprendizaje más suave que Angular, pero un ecosistema y comunidad más pequeños que ambos, y tampoco era una opción permitida por la consigna.
