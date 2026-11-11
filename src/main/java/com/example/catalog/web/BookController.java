package com.example.catalog.web;

import com.example.catalog.dto.BookRequestDTO;
import com.example.catalog.dto.BookResponseDTO;
import com.example.catalog.model.Book;
import com.example.catalog.repository.InMemoryBookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final InMemoryBookRepository repository;

    public BookController(InMemoryBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<BookResponseDTO> list() {
        return repository.findAll().stream()
                .map(BookResponseDTO::from)
                .toList();
    }

    @GetMapping("/{id}")
    public BookResponseDTO getOne(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(BookResponseDTO::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    @PostMapping
    public BookResponseDTO create(@RequestBody BookRequestDTO request) {
        Book saved = repository.save(request.toModel(null));
        return BookResponseDTO.from(saved);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id) { return "PUT /books/" + id; }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) { return "DELETE /books/" + id; }
}
