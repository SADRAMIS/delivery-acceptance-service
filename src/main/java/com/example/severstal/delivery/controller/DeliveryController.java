package com.example.severstal.delivery.controller;

import com.example.severstal.delivery.dto.DeliveryDto;
import com.example.severstal.delivery.dto.DeliveryItemResponseDto;
import com.example.severstal.delivery.dto.DeliveryResponseDto;
import com.example.severstal.delivery.entity.Delivery;
import com.example.severstal.delivery.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<DeliveryResponseDto> acceptDelivery(@Valid @RequestBody DeliveryDto dto) {
        Delivery delivery = deliveryService.acceptDelivery(dto);

        DeliveryResponseDto response = new DeliveryResponseDto(
                delivery.getId(),
                delivery.getSupplier().getId(),
                delivery.getDeliveryDate(),
                delivery.getItems().stream()
                        .map(item -> new DeliveryItemResponseDto(
                                item.getId(),
                                item.getProduct().getId(),
                                item.getWeightKg(),
                                item.getCost()
                        ))
                        .toList()
        );

        return ResponseEntity.ok(response);
    }
}
