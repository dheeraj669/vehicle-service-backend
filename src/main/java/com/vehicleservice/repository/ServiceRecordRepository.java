package com.vehicleservice.repository;

import com.vehicleservice.model.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {

    List<ServiceRecord> findByStatusIgnoreCase(String status);

    List<ServiceRecord> findByVehicleId(Long vehicleId);

    List<ServiceRecord> findByVehicleCustomerId(Long customerId);

    List<ServiceRecord> findTop10ByOrderByCreatedAtDesc();

    long countByStatusIgnoreCase(String status);

    @Query("SELECT COALESCE(SUM(s.totalCost), 0) FROM ServiceRecord s WHERE UPPER(s.status) IN ('COMPLETED', 'DELIVERED')")
    BigDecimal sumCompletedRevenue();

    @Query("SELECT s FROM ServiceRecord s WHERE " +
           "LOWER(s.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.serviceType) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.technicianName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.vehicle.vehicleNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.vehicle.customer.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<ServiceRecord> searchServices(@Param("query") String query);
}
