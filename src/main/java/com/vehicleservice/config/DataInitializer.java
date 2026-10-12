package com.vehicleservice.config;

import com.vehicleservice.model.Customer;
import com.vehicleservice.model.ServiceRecord;
import com.vehicleservice.model.Vehicle;
import com.vehicleservice.repository.CustomerRepository;
import com.vehicleservice.repository.ServiceRecordRepository;
import com.vehicleservice.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceRecordRepository serviceRecordRepository;

    public DataInitializer(CustomerRepository customerRepository,
                           VehicleRepository vehicleRepository,
                           ServiceRecordRepository serviceRecordRepository) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceRecordRepository = serviceRecordRepository;
    }

    @Override
    public void run(String... args) {
        if (customerRepository.count() > 0) {
            log.info("Database already initialized with data. Skipping sample seeding.");
            return;
        }

        log.info("Initializing Vehicle Service Management System sample data...");

        // 1. Create Customers
        Customer c1 = new Customer("Rahul Sharma", "rahul.sharma@example.com", "+91 98765 43210", "Plot 42, Hitech City, Hyderabad");
        Customer c2 = new Customer("Priya Patel", "priya.patel@example.com", "+91 98123 45678", "Flat 302, Green Glen Layout, Bangalore");
        Customer c3 = new Customer("Amit Verma", "amit.verma@example.com", "+91 97654 32109", "12B, Connaught Place, New Delhi");
        Customer c4 = new Customer("Sneha Reddy", "sneha.reddy@example.com", "+91 99887 76655", "Banjara Hills Road No. 10, Hyderabad");
        Customer c5 = new Customer("Vikram Malhotra", "vikram.m@example.com", "+91 91234 56789", "Park Street, Kolkata");

        customerRepository.saveAll(List.of(c1, c2, c3, c4, c5));

        // 2. Create Vehicles
        Vehicle v1 = new Vehicle(c1, "TS09EA1234", "Hyundai", "Creta SX(O)", 2022, "SUV", "PETROL", 24500);
        Vehicle v2 = new Vehicle(c1, "TS09FA5678", "Royal Enfield", "Classic 350", 2023, "MOTORCYCLE", "PETROL", 6200);
        Vehicle v3 = new Vehicle(c2, "KA01MJ4321", "Tata", "Nexon EV Empowered", 2023, "SUV", "ELECTRIC", 18300);
        Vehicle v4 = new Vehicle(c3, "DL03CP9876", "Honda", "City ZX CVT", 2021, "SEDAN", "PETROL", 38000);
        Vehicle v5 = new Vehicle(c4, "TS07KH1122", "Toyota", "Innova Hycross", 2024, "MUV", "HYBRID", 8900);
        Vehicle v6 = new Vehicle(c5, "WB02AD3344", "Mahindra", "XUV700 AX7L", 2022, "SUV", "DIESEL", 31200);

        vehicleRepository.saveAll(List.of(v1, v2, v3, v4, v5, v6));

        // 3. Create Service Records
        ServiceRecord s1 = new ServiceRecord(
                v1,
                "PERIODIC_MAINTENANCE",
                "25,000 KM General Service: Engine oil replacement, oil filter, air filter, and 40-point safety inspection.",
                "COMPLETED",
                "MEDIUM",
                LocalDate.now().minusDays(10),
                "Ramesh Kumar",
                new BigDecimal("1200.00"),
                new BigDecimal("3450.00"),
                "Fully synthetic engine oil used. All brake pads in good condition."
        );
        s1.setCompletionDate(LocalDate.now().minusDays(10));

        ServiceRecord s2 = new ServiceRecord(
                v2,
                "GENERAL_INSPECTION",
                "Chain lubrication, spark plug clean, clutch cable adjustment, and brake shoe check.",
                "DELIVERED",
                "LOW",
                LocalDate.now().minusDays(7),
                "Suresh G.",
                new BigDecimal("500.00"),
                new BigDecimal("250.00"),
                "Customer collected vehicle in person. Paid via UPI."
        );
        s2.setCompletionDate(LocalDate.now().minusDays(7));

        ServiceRecord s3 = new ServiceRecord(
                v3,
                "REPAIR",
                "Air conditioning cooling performance low, battery thermal balancing diagnostic test.",
                "IN_PROGRESS",
                "HIGH",
                LocalDate.now().minusDays(1),
                "Anand Verma",
                new BigDecimal("1500.00"),
                new BigDecimal("1800.00"),
                "AC refrigerant topped up; executing high voltage electrical system tests."
        );

        ServiceRecord s4 = new ServiceRecord(
                v4,
                "BRAKE_OVERHAUL",
                "Front brake pad worn out, squeaking sound reported under deceleration.",
                "WAITING_PARTS",
                "HIGH",
                LocalDate.now().minusDays(2),
                "Ramesh Kumar",
                new BigDecimal("800.00"),
                new BigDecimal("2900.00"),
                "Awaiting arrival of OEM brake pads from supplier."
        );

        ServiceRecord s5 = new ServiceRecord(
                v5,
                "PERIODIC_MAINTENANCE",
                "10,000 KM First Paid Service: Hybrid battery health check, tire rotation, wheel alignment.",
                "PENDING",
                "MEDIUM",
                LocalDate.now().plusDays(2),
                "Karthik Rao",
                new BigDecimal("1100.00"),
                new BigDecimal("1400.00"),
                "Customer requested morning pickup & drop facility."
        );

        ServiceRecord s6 = new ServiceRecord(
                v6,
                "OIL_CHANGE",
                "Synthetic engine oil & fuel filter replacement before long highway tour.",
                "COMPLETED",
                "URGENT",
                LocalDate.now().minusDays(3),
                "Ramesh Kumar",
                new BigDecimal("900.00"),
                new BigDecimal("4200.00"),
                "Mobil1 5W-30 filled, fuel filter replaced. Test driven successfully."
        );
        s6.setCompletionDate(LocalDate.now().minusDays(3));

        serviceRecordRepository.saveAll(List.of(s1, s2, s3, s4, s5, s6));

        log.info("Sample dataset seeded successfully. Total Customers: {}, Vehicles: {}, Services: {}",
                customerRepository.count(), vehicleRepository.count(), serviceRecordRepository.count());
    }
}
