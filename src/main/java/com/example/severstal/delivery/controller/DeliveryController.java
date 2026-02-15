package com.example.severstal.delivery.controller;

import com.example.severstal.delivery.dto.DeliveryDto;
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
    public ResponseEntity<Delivery> acceptDelivery(@Valid @RequestBody DeliveryDto dto) {
        Delivery delivery = deliveryService.acceptDelivery(dto);
        return ResponseEntity.ok(delivery);
    }
}
