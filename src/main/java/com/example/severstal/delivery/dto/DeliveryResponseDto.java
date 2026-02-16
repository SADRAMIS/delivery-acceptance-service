package com.example.severstal.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class DeliveryResponseDto {
    private Long id;
    private Long supplierId;
    private LocalDateTime deliveryDate;
    private List<DeliveryItemResponseDto> items;
}
