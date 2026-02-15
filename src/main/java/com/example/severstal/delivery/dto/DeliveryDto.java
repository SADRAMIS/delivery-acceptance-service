package com.example.severstal.delivery.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DeliveryDto {

    @NotNull(message = "supplierId не может быть null")
    private Long supplierId;

    @NotNull(message = "deliveryDate не может быть null")
    private LocalDateTime deliveryDate;

    @NotEmpty(message = "Список items не может быть пустым")
    @Valid
    private List<DeliveryItemDto> items;
}
