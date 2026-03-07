package com.store.application.service;

import com.store.application.dto.AdminDashboardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private  BrandService brandService;
    @Autowired
    private  CustomerService customerService;
    @Autowired
    private  SaleService salesService;


    public AdminDashboardData getDashboardData() {

        AdminDashboardData data = new AdminDashboardData();

        data.setTotalBrands(brandService.getTotalStock());

        data.setTotalStock(brandService.getTotalStock());

        data.setTotalCustomers(customerService.getTotalCustomers());

        data.setTotalSalesToday(salesService.getTodaySalesCount());

        data.setTotalRevenueToday(salesService.getTodayRevenue());

        return data;
    }
}
