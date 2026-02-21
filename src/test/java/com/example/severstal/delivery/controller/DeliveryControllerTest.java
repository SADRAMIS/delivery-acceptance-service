package com.example.severstal.delivery.controller;

import com.example.severstal.delivery.dto.DeliveryDto;
import com.example.severstal.delivery.entity.Delivery;
import com.example.severstal.delivery.entity.Supplier;
import com.example.severstal.delivery.service.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryControllerTest {

    @Mock
    private DeliveryService deliveryService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // ✅ МАНИУАЛЬНО создаём контроллер с МОКОМ
        DeliveryController deliveryController = new DeliveryController(deliveryService);
        mockMvc = MockMvcBuilders.standaloneSetup(deliveryController).build();
    }

    @Test
    void acceptDelivery_returnsOkAndDeliveryResponse() throws Exception {
        String deliveryJson = """
            {
                "supplierId": 1,
                "deliveryDate": "2026-02-06T10:00:00",
                "items": [
                    {
                        "productId": 1,
                        "weightKg": 10.5
                    }
                ]
            }
            """;

        Delivery mockDelivery = new Delivery();
        mockDelivery.setId(10L);

        Supplier mockSupplier = new Supplier();
        mockSupplier.setId(1L);
        mockDelivery.setSupplier(mockSupplier);

        when(deliveryService.acceptDelivery(any(DeliveryDto.class)))
                .thenReturn(mockDelivery);

        mockMvc.perform(post("/api/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deliveryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }
}
