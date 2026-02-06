package com.example.severstal.delivery.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DeliveryDto {
    private Long supplierId;
    private LocalDateTime deliveryDate;
    private List<DeliveryItemDto> items;
}
