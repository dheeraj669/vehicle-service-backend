package com.vehicleservice.controller;

import com.vehicleservice.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Map<String, Object>>> home() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("service", "Vehicle Service Management API");
        info.put("status", "UP");
        info.put("docs", "API is operational");
        info.put("sampleEndpoint", "/api/dashboard/stats");
        return ResponseEntity.ok(ApiResponse.ok("Vehicle Service API is running successfully", info));
    }
}
