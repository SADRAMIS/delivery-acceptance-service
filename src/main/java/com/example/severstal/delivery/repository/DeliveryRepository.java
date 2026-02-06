package com.example.severstal.delivery.repository;

import com.example.severstal.delivery.dto.ReportDto;
import com.example.severstal.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query(value = """
    SELECT 
        s.name AS supplierName,
        p.type AS productType,
        COALESCE(SUM(di.weight_kg), 0) AS totalWeight,
        COALESCE(SUM(di.cost), 0) AS totalCost
    FROM deliveries d 
    JOIN delivery_items di ON d.id = di.delivery_id 
    JOIN products p ON di.product_id = p.id 
    JOIN suppliers s ON p.supplier_id = s.id 
    WHERE d.delivery_date BETWEEN :startDate AND :endDate
    GROUP BY s.id, s.name, p.id, p.type 
    ORDER BY s.name, p.type
    """, nativeQuery = true)
    List<ReportDto> getReport(@Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);

}
