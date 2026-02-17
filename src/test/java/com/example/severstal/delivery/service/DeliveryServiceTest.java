package com.example.severstal.delivery.service;

import com.example.severstal.delivery.dto.DeliveryDto;
import com.example.severstal.delivery.dto.DeliveryItemDto;
import com.example.severstal.delivery.entity.*;
import com.example.severstal.delivery.exception.ProductDoesNotBelongToSupplierException;
import com.example.severstal.delivery.repository.DeliveryRepository;
import com.example.severstal.delivery.repository.ProductRepository;
import com.example.severstal.delivery.repository.SupplierRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class DeliveryServiceTest {

    private final SupplierRepository supplierRepository = mock(SupplierRepository.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final DeliveryRepository deliveryRepository = mock(DeliveryRepository.class);

    private final DeliveryService deliveryService = new DeliveryService(supplierRepository, productRepository, deliveryRepository);

    @Test
    void acceptDelivery_createsDeliveryWithItemsAndCost() {

        Long supplierId = 1L;
        Long productId1 = 1L;
        Long productId2 = 2L;

        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        supplier.setName("Поставщик1");


        Product product1 = new Product();
        product1.setId(productId1);
        product1.setSupplier(supplier);
        product1.setType("Груши_Золотистые");
        product1.setPricePerKg(BigDecimal.valueOf(150.50));

        Product product2 = new Product();
        product2.setId(productId2);
        product2.setSupplier(supplier);
        product2.setType("Груши_Конференс");
        product2.setPricePerKg(BigDecimal.valueOf(140.00));

        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier));
        when(productRepository.findById(productId1)).thenReturn(Optional.of(product1));
        when(productRepository.findById(productId2)).thenReturn(Optional.of(product2));

        when(deliveryRepository.save(any(Delivery.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        DeliveryItemDto itemDto1 = new DeliveryItemDto();
        itemDto1.setProductId(productId1);
        itemDto1.setWeightKg(10.5);

        DeliveryItemDto itemDto2 = new DeliveryItemDto();
        itemDto2.setProductId(productId2);
        itemDto2.setWeightKg(20.0);

        DeliveryDto dto = new DeliveryDto();
        dto.setSupplierId(supplierId);
        dto.setDeliveryDate(LocalDateTime.of(2026, 2, 6, 10, 0));
        dto.setItems(List.of(itemDto1, itemDto2));

        // when
        Delivery result = deliveryService.acceptDelivery(dto);

        // then
        assertThat(result.getSupplier().getId()).isEqualTo(supplierId);
        assertThat(result.getItems()).hasSize(2);

        DeliveryItem savedItem1 = result.getItems().get(0);
        DeliveryItem savedItem2 = result.getItems().get(1);

        assertThat(savedItem1.getProduct().getId()).isEqualTo(productId1);
        assertThat(savedItem1.getWeightKg()).isEqualByComparingTo(BigDecimal.valueOf(10.5));
        assertThat(savedItem1.getCost())
                .isEqualByComparingTo(BigDecimal.valueOf(10.5).multiply(BigDecimal.valueOf(150.50)));

        assertThat(savedItem2.getProduct().getId()).isEqualTo(productId2);
        assertThat(savedItem2.getWeightKg()).isEqualByComparingTo(BigDecimal.valueOf(20.0));
        assertThat(savedItem2.getCost())
                .isEqualByComparingTo(BigDecimal.valueOf(20.0).multiply(BigDecimal.valueOf(140.00)));

        verify(deliveryRepository, times(1)).save(any(Delivery.class));
    }

    @Test
    void acceptDelivery_throwsWhenProductNotBelongToSupplier() {
        Long supplierId = 1L;
        Long productId = 1L;

        Supplier supplier = new Supplier();
        supplier.setId(supplierId);

        Supplier otherSupplier = new Supplier();
        otherSupplier.setId(2L);

        Product product = new Product();
        product.setId(productId);
        product.setSupplier(otherSupplier); // чужой поставщик

        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        DeliveryItemDto itemDto = new DeliveryItemDto();
        itemDto.setProductId(productId);
        itemDto.setWeightKg(5.0);

        DeliveryDto dto = new DeliveryDto();
        dto.setSupplierId(supplierId);
        dto.setDeliveryDate(LocalDateTime.now());
        dto.setItems(List.of(itemDto));

        // expect
        assertThatThrownBy(() -> deliveryService.acceptDelivery(dto))
                .isInstanceOf(ProductDoesNotBelongToSupplierException.class);
    }
}
