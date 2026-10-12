package com.vehicleservice.dto;

import com.vehicleservice.model.ServiceRecord;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardStatsDTO {

    private long totalCustomers;
    private long totalVehicles;
    private long totalServices;
    private long pendingServices;
    private long inProgressServices;
    private long waitingPartsServices;
    private long completedServices;
    private long deliveredServices;
    private BigDecimal totalRevenue;
    private Map<String, Long> statusBreakdown;
    private List<ServiceRecord> recentServices;

    public DashboardStatsDTO() {
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalVehicles(long totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    public long getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(long totalServices) {
        this.totalServices = totalServices;
    }

    public long getPendingServices() {
        return pendingServices;
    }

    public void setPendingServices(long pendingServices) {
        this.pendingServices = pendingServices;
    }

    public long getInProgressServices() {
        return inProgressServices;
    }

    public void setInProgressServices(long inProgressServices) {
        this.inProgressServices = inProgressServices;
    }

    public long getWaitingPartsServices() {
        return waitingPartsServices;
    }

    public void setWaitingPartsServices(long waitingPartsServices) {
        this.waitingPartsServices = waitingPartsServices;
    }

    public long getCompletedServices() {
        return completedServices;
    }

    public void setCompletedServices(long completedServices) {
        this.completedServices = completedServices;
    }

    public long getDeliveredServices() {
        return deliveredServices;
    }

    public void setDeliveredServices(long deliveredServices) {
        this.deliveredServices = deliveredServices;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Map<String, Long> getStatusBreakdown() {
        return statusBreakdown;
    }

    public void setStatusBreakdown(Map<String, Long> statusBreakdown) {
        this.statusBreakdown = statusBreakdown;
    }

    public List<ServiceRecord> getRecentServices() {
        return recentServices;
    }

    public void setRecentServices(List<ServiceRecord> recentServices) {
        this.recentServices = recentServices;
    }
}
