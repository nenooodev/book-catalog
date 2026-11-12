package com.example.catalog.dto;

import com.example.catalog.model.Book;

/** Cuerpo de entrada para crear o actualizar un libro. */
public record BookRequestDTO(String isbn, String title, String author, int year) {

    public Book toModel(Long id) {
        return new Book(id, isbn, title, author, year);
    }
}
