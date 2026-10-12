-- ==============================================================================
-- VEHICLE SERVICE MANAGEMENT SYSTEM - SAMPLE DATA
-- ==============================================================================

USE vehicle_service_db;

-- Insert Customers
INSERT INTO customers (id, name, email, phone, address, created_at) VALUES
(1, 'Rahul Sharma', 'rahul.sharma@example.com', '+91 98765 43210', 'Plot 42, Hitech City, Hyderabad', NOW()),
(2, 'Priya Patel', 'priya.patel@example.com', '+91 98123 45678', 'Flat 302, Green Glen Layout, Bangalore', NOW()),
(3, 'Amit Verma', 'amit.verma@example.com', '+91 97654 32109', '12B, Connaught Place, New Delhi', NOW()),
(4, 'Sneha Reddy', 'sneha.reddy@example.com', '+91 99887 76655', 'Banjara Hills Road No. 10, Hyderabad', NOW()),
(5, 'Vikram Malhotra', 'vikram.m@example.com', '+91 91234 56789', 'Park Street, Kolkatta', NOW());

-- Insert Vehicles
INSERT INTO vehicles (id, customer_id, vehicle_number, make, model, model_year, vehicle_type, fuel_type, mileage, created_at) VALUES
(1, 1, 'TS09EA1234', 'Hyundai', 'Creta SX(O)', 2022, 'SUV', 'PETROL', 24500, NOW()),
(2, 1, 'TS09FA5678', 'Royal Enfield', 'Classic 350', 2023, 'MOTORCYCLE', 'PETROL', 6200, NOW()),
(3, 2, 'KA01MJ4321', 'Tata', 'Nexon EV Empowered', 2023, 'SUV', 'ELECTRIC', 18300, NOW()),
(4, 3, 'DL03CP9876', 'Honda', 'City ZX CVT', 2021, 'SEDAN', 'PETROL', 38000, NOW()),
(5, 4, 'TS07KH1122', 'Toyota', 'Innova Hycross', 2024, 'MUV', 'HYBRID', 8900, NOW()),
(6, 5, 'WB02AD3344', 'Mahindra', 'XUV700 AX7L', 2022, 'SUV', 'DIESEL', 31200, NOW());

-- Insert Service Records
INSERT INTO service_records (id, vehicle_id, service_type, description, status, priority, scheduled_date, completion_date, technician_name, labor_cost, parts_cost, total_cost, notes, created_at, updated_at) VALUES
(1, 1, 'PERIODIC_MAINTENANCE', '25,000 KM General Service: Engine oil replacement, oil filter, air filter, and 40-point safety inspection.', 'COMPLETED', 'MEDIUM', '2026-10-02', '2026-10-02', 'Ramesh Kumar', 1200.00, 3450.00, 4650.00, 'Synthetic engine oil used. All brake pads in good condition.', NOW(), NOW()),
(2, 2, 'GENERAL_INSPECTION', 'Chain lubrication, spark plug clean, clutch cable adjustment, and brake shoe check.', 'DELIVERED', 'LOW', '2026-10-05', '2026-10-05', 'Suresh G.', 500.00, 250.00, 750.00, 'Customer collected vehicle in person. Cash paid.', NOW(), NOW()),
(3, 3, 'REPAIR', 'Air conditioning cooling performance low, battery cell balancing diagnostic test.', 'IN_PROGRESS', 'HIGH', '2026-10-11', NULL, 'Anand Verma', 1500.00, 1800.00, 3300.00, 'AC refrigerant topped up, currently executing electrical thermal harness diagnostics.', NOW(), NOW()),
(4, 4, 'BRAKE_OVERHAUL', 'Front brake pad worn out, squeaking sound reported under heavy braking.', 'WAITING_PARTS', 'HIGH', '2026-10-10', NULL, 'Ramesh Kumar', 800.00, 2900.00, 3700.00, 'Waiting for OEM ceramic brake pads delivery from Honda warehouse.', NOW(), NOW()),
(5, 5, 'PERIODIC_MAINTENANCE', '10,000 KM First Paid Service: Hybrid battery health check, tire rotation, wheel alignment, and fluid checks.', 'PENDING', 'MEDIUM', '2026-10-14', NULL, 'Karthik Rao', 1100.00, 1400.00, 2500.00, 'Customer requested morning pickup drop facility.', NOW(), NOW()),
(6, 6, 'OIL_CHANGE', 'Synthetic engine oil & fuel filter replacement before long highway trip.', 'COMPLETED', 'URGENT', '2026-10-09', '2026-10-09', 'Ramesh Kumar', 900.00, 4200.00, 5100.00, 'Mobil1 5W-30 filled, fuel filter replaced. Test driven successfully.', NOW(), NOW());
