package com.store.application.dto;

public class AdminDashboardData {

    private int totalBrands;
    private int totalStock;
    private long totalCustomers;
    private long totalSalesToday;
    private double totalRevenueToday;

    public int getTotalBrands() {
        return totalBrands;
    }

    public void setTotalBrands(int totalBrands) {
        this.totalBrands = totalBrands;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getTotalSalesToday() {
        return totalSalesToday;
    }

    public void setTotalSalesToday(long totalSalesToday) {
        this.totalSalesToday = totalSalesToday;
    }

    public double getTotalRevenueToday() {
        return totalRevenueToday;
    }

    public void setTotalRevenueToday(double totalRevenueToday) {
        this.totalRevenueToday = totalRevenueToday;
    }
}