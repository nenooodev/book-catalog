# Equipo

| Nombre | Usuario GitHub | Slice o responsabilidad | Revisión realizada |
|---|---|---|---|
| Antonio | @nenooodev (confirmar) | Entregas 1–3: eco, CRUD JSON en memoria y ResponseEntity. Tests propios de flujo y documentación E1–E3. | Pendiente: revisar E4–E6 de @mamarod428 |
| Compañero/a (completar) | @mamarod428 | Entregas 4–6: `BookService`, excepciones propias y `GlobalExceptionHandler`. Tests unitarios del servicio. | Pendiente: revisar E1–E3 de Antonio |

Registrar contribuciones propias y colaboración; no compartir cuentas.

## Regla de slice vertical

Cada funcionalidad se entrega completa: test MockMvc → Controller →
repositorio/servicio → modelo/DTO → respuesta HTTP. No se aceptan commits que solo
añadan una capa sin su test y sin su camino HTTP real.

Ejemplo de la entrega 2, `GET /books`: test de listado vacío → controlador →
repositorio en memoria → `BookResponseDTO` → array JSON. `POST /books` añade
`BookRequestDTO`, generación de id y persistencia; `GET`, `PUT` y `DELETE` por id
recorren el mismo camino.

## Orden de integración

1. Antonio cierra E1–E3 en ramas `feat/entrega-N-antonio` y PR a `main`.
2. @mamarod428 parte del `main` con E3 verde para E4–E6.
3. Revisión cruzada: cada integrante revisa el slice del otro y lo registra aquí.
4. Regresión final: semilla 6 + todos los tests propios del CRUD.

## Convención de commits

- `[ROJO] feat|chore: entrega N - ...` test o estructura mínima que falla.
- `[VERDE] feat: entrega N - ...` implementación mínima que lo pone en verde.
- `[REFACTOR] chore: entrega N - ...` mejora sin cambiar comportamiento.
- `[DOCS] docs: entrega N - ...` README, aprendizaje o equipo.
- `[FIX] fix: entrega N - ...` corrección.

Cada commit es pequeño; se ejecuta el test afectado antes de commitear y la suite
completa antes de abrir el PR de la entrega.
