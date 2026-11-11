package com.example.catalog.web;

import com.example.catalog.dto.BookRequestDTO;
import com.example.catalog.dto.BookResponseDTO;
import com.example.catalog.model.Book;
import com.example.catalog.repository.InMemoryBookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final InMemoryBookRepository repository;

    public BookController(InMemoryBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> list() {
        List<BookResponseDTO> books = repository.findAll().stream()
                .map(BookResponseDTO::from)
                .toList();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getOne(@PathVariable("id") Long id) {
        BookResponseDTO book = repository.findById(id)
                .map(BookResponseDTO::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@RequestBody BookRequestDTO request) {
        Book saved = repository.save(request.toModel(null));
        BookResponseDTO response = BookResponseDTO.from(saved);
        return ResponseEntity.created(URI.create("/books/" + response.id())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(@PathVariable("id") Long id, @RequestBody BookRequestDTO request) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        Book saved = repository.save(request.toModel(id));
        return ResponseEntity.ok(BookResponseDTO.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (!repository.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return ResponseEntity.noContent().build();
    }
}
