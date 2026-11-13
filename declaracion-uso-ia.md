# Declaración de uso de IA

Completar por todos los integrantes, también si no se ha usado IA. No pegar
credenciales ni datos personales de terceros.

| Integrante | Herramienta/modelo o «no utilizada» | Uso concreto | Archivos/commits afectados | Cómo se verificó y qué se aprendió |
|---|---|---|---|---|
| Antonio (@nenooodev) | OpenCode con el modelo DeepSeek V4 Flash (redacción) + ChatGPT (consultas) | Redacción de la documentación del repositorio (`equipo.md` y `aprendizaje.md`) y uso a modo de tutor para comprender algunos conceptos a nivel de testing (tests de flujo con MockMvc, caracterización y qué cubre cada tipo de prueba) | `equipo.md` y `aprendizaje.md`: `ebe6049`, `653580e`, `682ad2e`, `4cf66db`, `e6d2db6`, `662e02b` (todos `[DOCS]`) | Revisé y adapté el texto a nuestro caso real, ejecuté la suite (`mvnw test`) para comprobar lo documentado y pedí explicaciones hasta poder defenderlo.|
| Manuel (@mamarod428) | Asistente IA (modo tutor y consulta técnica) | Consultas conceptuales sobre desacoplamiento de capas (aislar tipos HTTP del servicio), interpretación de trazas de fallo de Maven/Surefire y estructura de validación con Bean Validation (`@Valid`, `@NotBlank`) | `BookService.java`, `BookController.java`, `GlobalExceptionHandler.java`, `aprendizaje.md` (entregas 4, 5 y 6) | Desarrollé, integré y verifiqué el código en local ejecutando los tests paso a paso hasta tener la suite en verde y comprendí el flujo del `@RestControllerAdvice` para defenderlo oralmente. |

Cada integrante debe comprender y poder defender sus cambios. La IA puede
ayudar a interpretar errores y conceptos; no sustituye la autoría ni justifica
copiar una solución completa sin entenderla.
