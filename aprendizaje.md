# Registro de aprendizaje del grupo

## Entrega 1 — Eco de texto

- **Comportamiento que añadimos o contrato que evolucionamos:** se conserva el eco
  proporcionado por el esqueleto. Las cinco operaciones responden `200` con texto
  plano y el cuerpo indica el verbo y la ruta atendidos.
- **Test y rojo observado:** no hay rojo propio. El eco ya venía implementado en
  `BookController`, así que no se presenta como TDD nuestro. El test
  `BookEchoCharacterizationTest` es de caracterización y nace verde: fija el
  `Content-Type: text/plain`, los cinco cuerpos y que un id inexistente también se
  devuelve sin consultar nada.
- **Cambio mínimo y resultado verde:** no se modificó producción. El test propio
  documenta el contrato existente y la suite de la semilla 1 sigue verde.
- **Refactorización:** no procede en esta entrega.
- **Decisiones sobre capas y contrato HTTP:** el controlador es la única capa y no
  hay almacenamiento todavía. Las rutas son `GET /books`, `GET /books/{id}`,
  `POST /books`, `PUT /books/{id}` y `DELETE /books/{id}`. El `id` de ruta es un
  `Long`; el eco lo devuelve tal cual, sin comprobar si el libro existe.
- **Contribuciones, revisión y siguiente paso:** Antonio implementa y ejecuta el
  test de caracterización y documenta el reparto en `equipo.md`. La entrega 2
  sustituye el eco por JSON, así que este test se retirará y su historia queda en
  Git.

## Entrega 2 — CRUD JSON en memoria

- **Comportamiento que añadimos o contrato que evolucionamos:** el eco de texto se
  sustituye por JSON. Entrada `BookRequestDTO(isbn, title, author, year)` y salida
  `BookResponseDTO(id, isbn, title, author, year)`. El modelo interno `Book` no es
  una entidad JPA y no se expone. El primer `POST` de esta etapa responde `200`.
- **Test y rojo observado:** al activar la semilla 2, los tres tests fallan con
  `406` o con `Content-Type: text/plain` incompatible con `application/json`.
  Después se añadieron tests propios por cada operación del slice; cada uno se
  ejecutó antes de implementar y se vio el rojo correspondiente. Los ids
  inexistentes se cubren con `404` propio, sin adelantar las excepciones de la
  entrega 5.
- **Cambio mínimo y resultado verde:** se introducen `Book`,
  `BookRequestDTO`, `BookResponseDTO`, `InMemoryBookRepository` y el CRUD en el
  controlador. La colección vive en el repositorio; el controlador traduce HTTP y
  mapea a DTO. El listado se ordena por id y los ids se generan con `AtomicLong`.
- **Refactorización:** el mapeo se concentra en `BookResponseDTO.from(Book)` y
  `BookRequestDTO.toModel(Long)` para no repetirlo en cada endpoint. La suite
  completa queda verde.
- **Decisiones sobre capas y contrato HTTP:** `GET /books` devuelve un array;
  `GET /books/{id}` y `PUT /books/{id}` devuelven el DTO; `POST /books` devuelve el
  DTO con id; `DELETE /books/{id}` responde `200` en esta etapa. Se retira
  `produces = text/plain`. Los tests crean su propio recurso y siguen el id
  devuelto, sin suponer que existe el libro 1.
- **Contribuciones, revisión y siguiente paso:** Antonio implementa el CRUD y los
  tests de flujo. La entrega 3 cambiará el alta a `201 + Location` y el borrado a
  `204` sin cuerpo; los tests propios se adaptarán a ese contrato.

