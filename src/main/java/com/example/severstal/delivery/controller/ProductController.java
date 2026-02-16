package com.example.severstal.delivery.controller;

import com.example.severstal.delivery.dto.ProductDto;
import com.example.severstal.delivery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productRepository.findAll().stream()
                .map(p -> new ProductDto(
                        p.getId(),
                        p.getType(),
                        p.getPricePerKg(),
                        p.getSupplier().getId()
                ))
                .toList();
        return ResponseEntity.ok(products);
    }
}
