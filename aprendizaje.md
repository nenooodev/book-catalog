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
