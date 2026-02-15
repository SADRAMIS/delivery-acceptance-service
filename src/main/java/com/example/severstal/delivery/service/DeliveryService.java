package com.example.severstal.delivery.service;

import com.example.severstal.delivery.dto.DeliveryDto;
import com.example.severstal.delivery.entity.*;
import com.example.severstal.delivery.exception.ProductDoesNotBelongToSupplierException;
import com.example.severstal.delivery.exception.ProductNotFoundException;
import com.example.severstal.delivery.exception.SupplierNotFoundException;
import com.example.severstal.delivery.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;
    @Transactional
    public Delivery acceptDelivery(DeliveryDto dto) {
        // Проверяем поставщика
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new SupplierNotFoundException(dto.getSupplierId()));

        // Создаём поставку
        Delivery delivery = new Delivery();
        delivery.setSupplier(supplier);
        delivery.setDeliveryDate(dto.getDeliveryDate());

        // Обрабатываем позиции
        dto.getItems().forEach(itemDto -> {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemDto.getProductId()));

            if (!product.getSupplier().getId().equals(supplier.getId())) {
                throw new ProductDoesNotBelongToSupplierException(product.getId(), supplier.getId());
            }

            DeliveryItem item = new DeliveryItem();
            item.setDelivery(delivery);
            item.setProduct(product);

            // вес из DTO (Double) превращаем в BigDecimal
            BigDecimal weight = BigDecimal.valueOf(itemDto.getWeightKg());
            item.setWeightKg(weight);

            // стоимость = вес * цена за кг (оба BigDecimal)
            BigDecimal cost = weight.multiply(product.getPricePerKg());
            item.setCost(cost);

            delivery.getItems().add(item);
        });

        return deliveryRepository.save(delivery);
    }
}
