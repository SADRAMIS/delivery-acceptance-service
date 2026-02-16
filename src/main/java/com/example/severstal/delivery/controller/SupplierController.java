package com.example.severstal.delivery.controller;

import com.example.severstal.delivery.dto.SupplierDto;
import com.example.severstal.delivery.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierRepository supplierRepository;

    @GetMapping
    public ResponseEntity<List<SupplierDto>> getSuppliers() {
        List<SupplierDto> suppliers = supplierRepository.findAll().stream()
                .map(s -> new SupplierDto(s.getId(), s.getName()))
                .toList();
        return ResponseEntity.ok(suppliers);
    }
}
