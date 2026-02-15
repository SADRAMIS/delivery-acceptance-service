package com.example.severstal.delivery.exception;

public class ProductDoesNotBelongToSupplierException extends RuntimeException {
    public ProductDoesNotBelongToSupplierException(Long productId, Long supplierId) {
        super("Продукт с id = " + productId + " не принадлежит поставщику с id = " + supplierId);
    }
}
