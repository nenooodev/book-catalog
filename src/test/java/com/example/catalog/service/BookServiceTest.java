package com.example.catalog.service;

import com.example.catalog.dto.BookRequestDTO;
import com.example.catalog.dto.BookResponseDTO;
import com.example.catalog.repository.InMemoryBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private InMemoryBookRepository repository;
    private BookService service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryBookRepository();
        service = new BookService(repository);
    }

    @Test
    void createAndGetBook() {
        BookRequestDTO request = new BookRequestDTO("978-1", "Clean Code", "Robert C. Martin", 2008);
        BookResponseDTO created = service.create(request);

        assertNotNull(created.id());
        assertEquals("978-1", created.isbn());
        assertEquals("Clean Code", created.title());

        BookResponseDTO found = service.get(created.id());
        assertEquals(created, found);
    }

    @Test
    void listReturnsAllSavedBooks() {
        service.create(new BookRequestDTO("978-1", "Book 1", "Author 1", 2001));
        service.create(new BookRequestDTO("978-2", "Book 2", "Author 2", 2002));

        List<BookResponseDTO> list = service.list();
        assertEquals(2, list.size());
    }

    @Test
    void updateExistingBook() {
        BookResponseDTO created = service.create(new BookRequestDTO("978-1", "Old Title", "Author", 2000));
        BookRequestDTO updateRequest = new BookRequestDTO("978-1", "New Title", "Author", 2000);

        BookResponseDTO updated = service.update(created.id(), updateRequest);
        assertNotNull(updated);
        assertEquals("New Title", updated.title());
        assertEquals(created.id(), updated.id());
    }

    @Test
    void deleteRemovesBook() {
        BookResponseDTO created = service.create(new BookRequestDTO("978-1", "To Delete", "Author", 2020));
        service.delete(created.id());

        assertThrows(BookNotFoundException.class, () -> service.get(created.id()));
    }

    @Test
    void getMissingThrowsNotFound() {
        assertThrows(BookNotFoundException.class, () -> service.get(999L));
    }

    @Test
    void createNullThrowsInvalidBook() {
        assertThrows(InvalidBookException.class, () -> service.create(null));
    }

    @Test
    void deleteMissingThrowsNotFound() {
        assertThrows(BookNotFoundException.class, () -> service.delete(999L));
    }
}
