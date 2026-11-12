package com.example.catalog;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    private long create() throws Exception {
        String json = mvc.perform(post("/books").contentType("application/json").content(BOOK))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("DDD"))
                .andReturn().getResponse().getContentAsString();
        return ((Number) JsonPath.read(json, "$.id")).longValue();
    }

    @Test
    void emptyCatalogReturnsJsonArray() throws Exception {
        mvc.perform(get("/books").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void createReturnsGeneratedIdAndFields() throws Exception {
        long id = create();

        mvc.perform(get("/books").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].isbn").value("978-1"))
                .andExpect(jsonPath("$[0].author").value("Evans"))
                .andExpect(jsonPath("$[0].year").value(2003));
    }

    @Test
    void createAssignsDistinctIds() throws Exception {
        long first = create();
        long second = create();

        assertNotEquals(first, second);
        mvc.perform(get("/books")).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getReturnsCreatedBook() throws Exception {
        long id = create();

        mvc.perform(get("/books/" + id).accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.isbn").value("978-1"))
                .andExpect(jsonPath("$.title").value("DDD"))
                .andExpect(jsonPath("$.author").value("Evans"))
                .andExpect(jsonPath("$.year").value(2003));
    }

    @Test
    void getMissingReturns404() throws Exception {
        mvc.perform(get("/books/999999")).andExpect(status().isNotFound());
    }

    @Test
    void updateKeepsIdAndChangesFields() throws Exception {
        long id = create();

        mvc.perform(put("/books/" + id).contentType("application/json").content("""
                        {"isbn":"978-2","title":"Refactoring","author":"Fowler","year":2018}
                        """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.isbn").value("978-2"))
                .andExpect(jsonPath("$.title").value("Refactoring"))
                .andExpect(jsonPath("$.author").value("Fowler"))
                .andExpect(jsonPath("$.year").value(2018));

        mvc.perform(get("/books/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Refactoring"));
    }

    @Test
    void updateMissingReturns404() throws Exception {
        mvc.perform(put("/books/999999").contentType("application/json").content(BOOK))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRemovesOnlyCreatedBook() throws Exception {
        long first = create();
        long second = create();

        mvc.perform(delete("/books/" + first)).andExpect(status().isOk());

        mvc.perform(get("/books"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(second));
        mvc.perform(get("/books/" + first)).andExpect(status().isNotFound());
        mvc.perform(get("/books/" + second)).andExpect(status().isOk());
    }

    @Test
    void deleteMissingReturns404() throws Exception {
        mvc.perform(delete("/books/999999")).andExpect(status().isNotFound());
    }
}
