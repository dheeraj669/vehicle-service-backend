package com.vehicleservice.service;

import com.vehicleservice.dto.ServiceRecordRequest;
import com.vehicleservice.dto.StatusUpdateRequest;
import com.vehicleservice.exception.ResourceNotFoundException;
import com.vehicleservice.model.ServiceRecord;
import com.vehicleservice.model.Vehicle;
import com.vehicleservice.repository.ServiceRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ServiceRecordService {

    private final ServiceRecordRepository serviceRecordRepository;
    private final VehicleService vehicleService;

    public ServiceRecordService(ServiceRecordRepository serviceRecordRepository, VehicleService vehicleService) {
        this.serviceRecordRepository = serviceRecordRepository;
        this.vehicleService = vehicleService;
    }

    @Transactional(readOnly = true)
    public List<ServiceRecord> getAllServices() {
        return serviceRecordRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ServiceRecord getServiceById(Long id) {
        return serviceRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service record not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<ServiceRecord> getServicesByStatus(String status) {
        return serviceRecordRepository.findByStatusIgnoreCase(status.trim());
    }

    @Transactional(readOnly = true)
    public List<ServiceRecord> getServicesByVehicleId(Long vehicleId) {
        return serviceRecordRepository.findByVehicleId(vehicleId);
    }

    @Transactional(readOnly = true)
    public List<ServiceRecord> getServicesByCustomerId(Long customerId) {
        return serviceRecordRepository.findByVehicleCustomerId(customerId);
    }

    public ServiceRecord createService(ServiceRecordRequest request) {
        Vehicle vehicle = vehicleService.getVehicleById(request.getVehicleId());

        ServiceRecord record = new ServiceRecord(
                vehicle,
                request.getServiceType().trim(),
                request.getDescription().trim(),
                request.getStatus() != null ? request.getStatus().trim().toUpperCase() : "PENDING",
                request.getPriority() != null ? request.getPriority().trim().toUpperCase() : "MEDIUM",
                request.getScheduledDate(),
                request.getTechnicianName(),
                request.getLaborCost() != null ? request.getLaborCost() : BigDecimal.ZERO,
                request.getPartsCost() != null ? request.getPartsCost() : BigDecimal.ZERO,
                request.getNotes()
        );
        if (request.getCompletionDate() != null) {
            record.setCompletionDate(request.getCompletionDate());
        }
        return serviceRecordRepository.save(record);
    }

    public ServiceRecord updateService(Long id, ServiceRecordRequest request) {
        ServiceRecord existing = getServiceById(id);

        if (!existing.getVehicle().getId().equals(request.getVehicleId())) {
            Vehicle newVehicle = vehicleService.getVehicleById(request.getVehicleId());
            existing.setVehicle(newVehicle);
        }

        existing.setServiceType(request.getServiceType().trim());
        existing.setDescription(request.getDescription().trim());
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus().trim().toUpperCase());
        }
        if (request.getPriority() != null) {
            existing.setPriority(request.getPriority().trim().toUpperCase());
        }
        existing.setScheduledDate(request.getScheduledDate());
        existing.setCompletionDate(request.getCompletionDate());
        existing.setTechnicianName(request.getTechnicianName());
        existing.setLaborCost(request.getLaborCost() != null ? request.getLaborCost() : BigDecimal.ZERO);
        existing.setPartsCost(request.getPartsCost() != null ? request.getPartsCost() : BigDecimal.ZERO);
        existing.setNotes(request.getNotes());
        existing.recalculateTotal();

        return serviceRecordRepository.save(existing);
    }

    public ServiceRecord updateStatus(Long id, StatusUpdateRequest request) {
        ServiceRecord existing = getServiceById(id);
        String newStatus = request.getStatus().trim().toUpperCase();
        existing.setStatus(newStatus);

        if ("COMPLETED".equals(newStatus) || "DELIVERED".equals(newStatus)) {
            if (existing.getCompletionDate() == null) {
                existing.setCompletionDate(request.getCompletionDate() != null ? request.getCompletionDate() : LocalDate.now());
            }
        }

        if (request.getNotes() != null && !request.getNotes().trim().isEmpty()) {
            String combined = (existing.getNotes() != null ? existing.getNotes() + "\n" : "") +
                    "[" + LocalDate.now() + " Status changed to " + newStatus + "]: " + request.getNotes();
            existing.setNotes(combined);
        }

        if (request.getLaborCost() != null) {
            existing.setLaborCost(request.getLaborCost());
        }
        if (request.getPartsCost() != null) {
            existing.setPartsCost(request.getPartsCost());
        }
        existing.recalculateTotal();

        return serviceRecordRepository.save(existing);
    }

    public void deleteService(Long id) {
        ServiceRecord existing = getServiceById(id);
        serviceRecordRepository.delete(existing);
    }

    @Transactional(readOnly = true)
    public List<ServiceRecord> searchServices(String query) {
        if (query == null || query.trim().isEmpty()) {
            return serviceRecordRepository.findAll();
        }
        return serviceRecordRepository.searchServices(query.trim());
    }
}
