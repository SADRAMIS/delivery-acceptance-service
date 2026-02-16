package com.example.severstal.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DeliveryItemResponseDto {
    private Long id;
    private Long productId;
    private BigDecimal weightKg;
    private BigDecimal cost;
}
