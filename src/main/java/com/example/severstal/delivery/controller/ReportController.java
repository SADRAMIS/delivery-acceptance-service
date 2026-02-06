package com.example.severstal.delivery.controller;

import com.example.severstal.delivery.dto.ReportDto;
import com.example.severstal.delivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final DeliveryRepository deliveryRepository;

    @GetMapping
    public ResponseEntity<List<ReportDto>> getReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<ReportDto> report = deliveryRepository.getReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }
}
