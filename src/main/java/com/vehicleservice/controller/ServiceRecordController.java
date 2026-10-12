package com.vehicleservice.controller;

import com.vehicleservice.dto.ApiResponse;
import com.vehicleservice.dto.ServiceRecordRequest;
import com.vehicleservice.dto.StatusUpdateRequest;
import com.vehicleservice.model.ServiceRecord;
import com.vehicleservice.service.ServiceRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceRecordController {

    private final ServiceRecordService serviceRecordService;

    public ServiceRecordController(ServiceRecordService serviceRecordService) {
        this.serviceRecordService = serviceRecordService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceRecord>>> getAllServices(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {
        List<ServiceRecord> list;
        if (status != null && !status.trim().isEmpty()) {
            list = serviceRecordService.getServicesByStatus(status);
        } else if (search != null && !search.trim().isEmpty()) {
            list = serviceRecordService.searchServices(search);
        } else {
            list = serviceRecordService.getAllServices();
        }
        return ResponseEntity.ok(ApiResponse.ok("Service records retrieved", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceRecord>> getServiceById(@PathVariable Long id) {
        ServiceRecord record = serviceRecordService.getServiceById(id);
        return ResponseEntity.ok(ApiResponse.ok("Service record found", record));
    }

    @GetMapping("/by-vehicle/{vehicleId}")
    public ResponseEntity<ApiResponse<List<ServiceRecord>>> getServicesByVehicle(@PathVariable Long vehicleId) {
        List<ServiceRecord> list = serviceRecordService.getServicesByVehicleId(vehicleId);
        return ResponseEntity.ok(ApiResponse.ok("Service history for vehicle retrieved", list));
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<ApiResponse<List<ServiceRecord>>> getServicesByCustomer(@PathVariable Long customerId) {
        List<ServiceRecord> list = serviceRecordService.getServicesByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.ok("Service history for customer retrieved", list));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ServiceRecord>> createService(@Valid @RequestBody ServiceRecordRequest request) {
        ServiceRecord created = serviceRecordService.createService(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Service record created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceRecord>> updateService(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRecordRequest request) {
        ServiceRecord updated = serviceRecordService.updateService(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Service record updated successfully", updated));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ServiceRecord>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest request) {
        ServiceRecord updated = serviceRecordService.updateStatus(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Service status updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable Long id) {
        serviceRecordService.deleteService(id);
        return ResponseEntity.ok(ApiResponse.ok("Service record deleted successfully", null));
    }
}
