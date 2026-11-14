package com.example.catalog.dto;

import com.example.catalog.model.Book;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BookRequestDTO(
        @NotBlank(message = "El ISBN no puede estar en blanco") String isbn,
        @NotBlank(message = "El título no puede estar en blanco") String title,
        @NotBlank(message = "El autor no puede estar en blanco") String author,
        @Min(value = 0, message = "El año debe ser mayor o igual a 0") int year
) {
    public Book toModel(Long id) {
        return new Book(id, isbn, title, author, year);
    }
}