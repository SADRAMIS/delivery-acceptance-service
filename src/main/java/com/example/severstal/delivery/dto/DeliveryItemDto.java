package com.example.severstal.delivery.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DeliveryItemDto {

    @NotNull(message = "productId не может быть null")
    private Long productId;

    @NotNull(message = "weightKg не может быть null")
    @Positive(message = "weightKg должен быть > 0")
    private Double weightKg;
}
