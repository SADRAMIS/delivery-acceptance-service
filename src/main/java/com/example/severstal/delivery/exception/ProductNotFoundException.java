package com.example.severstal.delivery.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("Продукт не найден: id = " + productId);
    }
}
