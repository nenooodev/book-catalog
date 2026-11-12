package com.example.catalog.repository;

import com.example.catalog.model.Book;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Almacenamiento en memoria. En la entrega 4 la lógica de negocio se moverá a un
 * servicio; este repositorio seguirá siendo el encargado de conservar los libros.
 */
@Repository
public class InMemoryBookRepository {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public List<Book> findAll() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::id))
                .toList();
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public Book save(Book book) {
        long id = book.id() == null ? sequence.incrementAndGet() : book.id();
        Book stored = new Book(id, book.isbn(), book.title(), book.author(), book.year());
        books.put(id, stored);
        return stored;
    }

    public boolean deleteById(Long id) {
        return books.remove(id) != null;
    }
}
