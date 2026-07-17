# Benchmark de cache — GET /api/libros

## Metodología

Se midió el tiempo de respuesta de `GET /api/libros` en dos escenarios, con el backend y Redis corriendo localmente (Docker Compose):

- **Sin cache**: se vació la cache (`redis-cli FLUSHDB`) antes de cada una de las 10 repeticiones, forzando que cada petición golpee la base de datos.
- **Con cache**: se vació la cache una sola vez, se hizo una petición de "calentamiento" (no medida) para poblarla, y luego se midieron 10 repeticiones consecutivas dentro de la ventana de TTL (5 minutos), todas sirviéndose desde Redis.

El speedup se calcula como `S = Tsin / Tcon` (tiempo promedio sin cache dividido entre tiempo promedio con cache).

Script usado: `backend/scripts/benchmark-cache.ps1`.

## Resultados crudos

| Repetición | Sin cache (ms) | Con cache (ms) |
|---|---|---|
| 1 | 429.17 | 28.03 |
| 2 | 14.80 | 8.47 |
| 3 | 14.88 | 7.85 |
| 4 | 13.31 | 9.90 |
| 5 | 18.06 | 7.43 |
| 6 | 14.64 | 9.45 |
| 7 | 15.37 | 7.34 |
| 8 | 14.27 | 8.75 |
| 9 | 13.25 | 8.40 |
| 10 | 13.70 | 10.15 |

## Interpretación

La primera repetición de cada bloque muestra un pico (429.17 ms sin cache, 28.03 ms con cache) atribuible al arranque en frío de la JVM y del pool de conexiones de HikariCP hacia PostgreSQL, no al comportamiento del cache en sí. Por eso se reportan dos cálculos:

| Cálculo | Promedio sin cache | Promedio con cache | Speedup S |
|---|---|---|---|
| Con todos los datos (incluye arranque en frío) | 56.15 ms | 10.58 ms | **5.31x** |
| Excluyendo la 1ª repetición de cada bloque (sistema ya "caliente") | 14.70 ms | 8.64 ms | **1.70x** |

El primer número (5.31x) sobreestima el beneficio real del cache porque compara un caso frío contra uno ya calentado. El segundo número (1.70x) es más representativo del beneficio que aporta Redis en un sistema en régimen estable: una reducción de aproximadamente el 41% en el tiempo de respuesta al evitar la consulta a PostgreSQL.

## Conclusión

El cache-aside implementado con `@Cacheable`/`@CacheEvict` sobre `GET /api/libros` reduce el tiempo de respuesta de forma consistente. El beneficio medido en condiciones estables (sin efectos de arranque en frío) es de aproximadamente **1.7x**, cifra que crecería en escenarios con consultas más costosas (más registros, joins, filtros complejos) donde el costo de ir a base de datos pesa más frente al costo fijo de leer de Redis.