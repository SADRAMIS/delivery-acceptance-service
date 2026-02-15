package com.example.severstal.delivery.exception;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(Long supplierId) {
        super("Поставщик не найден: id = " + supplierId);
    }
}
