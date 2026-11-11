package com.example.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Flujo HTTP real del CRUD en memoria.
 *
 * <p>Cada test crea sus propios recursos y sigue el id devuelto. El contexto se
 * reinicia después de cada método porque la colección es estado compartido del
 * bean repositorio y varios tests comprueban el contenido del catálogo.</p>
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookCrudFlowTest {

    private static final String BOOK = """
            {"isbn":"978-1","title":"DDD","author":"Evans","year":2003}
            """;

    @Autowired
    MockMvc mvc;

    @Test
    void emptyCatalogReturnsJsonArray() throws Exception {
        mvc.perform(get("/books").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
