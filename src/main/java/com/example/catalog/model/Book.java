package com.example.catalog.model;

/**
 * Modelo interno del catálogo. No es una entidad JPA: el almacenamiento es en
 * memoria y el controlador nunca lo expone directamente.
 */
public record Book(Long id, String isbn, String title, String author, int year) {
}
