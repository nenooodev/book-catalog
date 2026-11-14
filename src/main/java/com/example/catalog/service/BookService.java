package com.example.catalog.service;

import com.example.catalog.dto.BookRequestDTO;
import com.example.catalog.dto.BookResponseDTO;
import com.example.catalog.model.Book;
import com.example.catalog.repository.InMemoryBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final InMemoryBookRepository repository;

    public BookService(InMemoryBookRepository repository) {
        this.repository = repository;
    }

    public List<BookResponseDTO> list() {
        return repository.findAll().stream()
                .map(BookResponseDTO::from)
                .toList();
    }

    public BookResponseDTO get(Long id) {
        return repository.findById(id)
                .map(BookResponseDTO::from)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    public BookResponseDTO create(BookRequestDTO request) {
        if (request == null || request.isbn() == null || request.title() == null) {
            throw new InvalidBookException("Book not accepted");
        }
        Book saved = repository.save(request.toModel(null));
        return BookResponseDTO.from(saved);
    }

    public BookResponseDTO update(Long id, BookRequestDTO request) {
        if (request == null || request.isbn() == null || request.title() == null) {
            throw new InvalidBookException("Book not accepted");
        }
        return repository.findById(id)
                .map(existing -> {
                    Book updated = repository.save(request.toModel(id));
                    return BookResponseDTO.from(updated);
                })
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new BookNotFoundException("Book not found");
        }
    }
}
