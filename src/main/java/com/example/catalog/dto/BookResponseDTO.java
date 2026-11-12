package com.example.catalog.dto;

import com.example.catalog.model.Book;

/** Representación de salida del contrato HTTP. */
public record BookResponseDTO(Long id, String isbn, String title, String author, int year) {

    public static BookResponseDTO from(Book book) {
        return new BookResponseDTO(
                book.id(),
                book.isbn(),
                book.title(),
                book.author(),
                book.year()
        );
    }
}
