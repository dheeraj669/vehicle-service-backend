package com.vehicleservice.service;

import com.vehicleservice.dto.DashboardStatsDTO;
import com.vehicleservice.repository.CustomerRepository;
import com.vehicleservice.repository.ServiceRecordRepository;
import com.vehicleservice.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceRecordRepository serviceRecordRepository;

    public DashboardService(CustomerRepository customerRepository,
                            VehicleRepository vehicleRepository,
                            ServiceRecordRepository serviceRecordRepository) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceRecordRepository = serviceRecordRepository;
    }

    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        stats.setTotalCustomers(customerRepository.count());
        stats.setTotalVehicles(vehicleRepository.count());
        stats.setTotalServices(serviceRecordRepository.count());

        long pending = serviceRecordRepository.countByStatusIgnoreCase("PENDING");
        long inProgress = serviceRecordRepository.countByStatusIgnoreCase("IN_PROGRESS");
        long waitingParts = serviceRecordRepository.countByStatusIgnoreCase("WAITING_PARTS");
        long completed = serviceRecordRepository.countByStatusIgnoreCase("COMPLETED");
        long delivered = serviceRecordRepository.countByStatusIgnoreCase("DELIVERED");

        stats.setPendingServices(pending);
        stats.setInProgressServices(inProgress);
        stats.setWaitingPartsServices(waitingParts);
        stats.setCompletedServices(completed);
        stats.setDeliveredServices(delivered);

        BigDecimal revenue = serviceRecordRepository.sumCompletedRevenue();
        stats.setTotalRevenue(revenue != null ? revenue : BigDecimal.ZERO);

        Map<String, Long> breakdown = new HashMap<>();
        breakdown.put("PENDING", pending);
        breakdown.put("IN_PROGRESS", inProgress);
        breakdown.put("WAITING_PARTS", waitingParts);
        breakdown.put("COMPLETED", completed);
        breakdown.put("DELIVERED", delivered);
        stats.setStatusBreakdown(breakdown);

        stats.setRecentServices(serviceRecordRepository.findTop10ByOrderByCreatedAtDesc());

        return stats;
    }
}
