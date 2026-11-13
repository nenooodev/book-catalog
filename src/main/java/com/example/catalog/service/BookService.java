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
                .orElse(null);
    }

    public BookResponseDTO create(BookRequestDTO request) {
        Book saved = repository.save(request.toModel(null));
        return BookResponseDTO.from(saved);
    }

    public BookResponseDTO update(Long id, BookRequestDTO request) {
        return repository.findById(id)
                .map(existing -> {
                    Book updated = repository.save(request.toModel(id));
                    return BookResponseDTO.from(updated);
                })
                .orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}