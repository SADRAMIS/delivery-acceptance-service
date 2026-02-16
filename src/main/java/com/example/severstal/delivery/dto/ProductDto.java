package com.example.severstal.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String type;
    private BigDecimal pricePerKg;
    private Long supplierId;
}
