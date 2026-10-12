package com.vehicleservice.service;

import com.vehicleservice.dto.VehicleRequest;
import com.vehicleservice.exception.DuplicateResourceException;
import com.vehicleservice.exception.ResourceNotFoundException;
import com.vehicleservice.model.Customer;
import com.vehicleservice.model.Vehicle;
import com.vehicleservice.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerService customerService;

    public VehicleService(VehicleRepository vehicleRepository, CustomerService customerService) {
        this.vehicleRepository = vehicleRepository;
        this.customerService = customerService;
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicleByNumber(String vehicleNumber) {
        return vehicleRepository.findByVehicleNumberIgnoreCase(vehicleNumber.trim())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with registration number: " + vehicleNumber));
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getVehiclesByCustomerId(Long customerId) {
        return vehicleRepository.findByCustomerId(customerId);
    }

    public Vehicle createVehicle(VehicleRequest request) {
        String regNo = request.getVehicleNumber().trim().toUpperCase();
        if (vehicleRepository.existsByVehicleNumberIgnoreCase(regNo)) {
            throw new DuplicateResourceException("Vehicle registration number already exists: " + regNo);
        }
        Customer customer = customerService.getCustomerById(request.getCustomerId());

        Vehicle vehicle = new Vehicle(
                customer,
                regNo,
                request.getMake().trim(),
                request.getModel().trim(),
                request.getYear(),
                request.getVehicleType().trim().toUpperCase(),
                request.getFuelType().trim().toUpperCase(),
                request.getMileage() != null ? request.getMileage() : 0
        );
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, VehicleRequest request) {
        Vehicle existing = getVehicleById(id);
        String regNo = request.getVehicleNumber().trim().toUpperCase();

        if (!existing.getVehicleNumber().equalsIgnoreCase(regNo) &&
                vehicleRepository.existsByVehicleNumberIgnoreCase(regNo)) {
            throw new DuplicateResourceException("Vehicle registration number already taken: " + regNo);
        }

        if (!existing.getCustomer().getId().equals(request.getCustomerId())) {
            Customer newCustomer = customerService.getCustomerById(request.getCustomerId());
            existing.setCustomer(newCustomer);
        }

        existing.setVehicleNumber(regNo);
        existing.setMake(request.getMake().trim());
        existing.setModel(request.getModel().trim());
        existing.setYear(request.getYear());
        existing.setVehicleType(request.getVehicleType().trim().toUpperCase());
        existing.setFuelType(request.getFuelType().trim().toUpperCase());
        if (request.getMileage() != null) {
            existing.setMileage(request.getMileage());
        }
        return vehicleRepository.save(existing);
    }

    public void deleteVehicle(Long id) {
        Vehicle existing = getVehicleById(id);
        vehicleRepository.delete(existing);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> searchVehicles(String query) {
        if (query == null || query.trim().isEmpty()) {
            return vehicleRepository.findAll();
        }
        return vehicleRepository.searchVehicles(query.trim());
    }
}
