package com.example.catalog;

import com.example.catalog.web.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test de caracterización del eco proporcionado en la entrega 1.
 *
 * <p>No es TDD propio: el comportamiento ya existe en el esqueleto. Se añade para
 * documentar que las cinco rutas responden en texto plano y que el eco no consulta
 * ningún almacenamiento.</p>
 */
@WebMvcTest(BookController.class)
class BookEchoCharacterizationTest {

    @Autowired
    MockMvc mvc;

    @Test
    void allEchoEndpointsUseTextPlain() throws Exception {
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("GET /books"));

        mvc.perform(get("/books/999"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("GET /books/999"));

        mvc.perform(post("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("POST /books"));

        mvc.perform(put("/books/999"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("PUT /books/999"));

        mvc.perform(delete("/books/999"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("DELETE /books/999"));
    }

    @Test
    void echoDoesNotLookUpTheBook() throws Exception {
        mvc.perform(get("/books/424242"))
                .andExpect(status().isOk())
                .andExpect(content().string("GET /books/424242"));
    }
}
