package com.store.application.service;

import com.store.application.dto.SaleItemDTO;
import com.store.application.dto.SaleRequestDTO;
import com.store.application.model.Brand;
import com.store.application.model.Customer;
import com.store.application.model.Sale;
import com.store.application.repository.BrandRepository;
import com.store.application.repository.CustomerRepository;
import com.store.application.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private  BrandRepository brandRepo;
    @Autowired
    private  SaleRepository saleRepo;
    @Autowired
    private  CustomerRepository customerRepo;


    @Transactional
    public void processSale(SaleRequestDTO saleRequest) {

        Customer customer = customerRepo.findByMobileNumber(saleRequest.getCustomerMobile())
                .orElseGet(() -> {
                    Customer newCust = new Customer();
                    newCust.setMobileNumber(saleRequest.getCustomerMobile());
                    newCust.setName(saleRequest.getCustomerName());
                    return customerRepo.save(newCust);
                });

        List<Sale> sales = saleRequest.getItems().stream()
                .filter(item -> item.getBrandId() != null)
                .map(item -> {

                    Brand brand = brandRepo.findById(item.getBrandId())
                            .orElseThrow(() -> new RuntimeException("Brand not found"));

                    int quantity = item.getQuantity();

                    if (brand.getStockCount() < quantity) {
                        throw new RuntimeException("Only " + brand.getStockCount() + " items available for " + brand.getName());
                    }
                    brand.setStockCount(brand.getStockCount() - quantity);
                    Sale sale = new Sale();
                    sale.setBrand(brand);
                    sale.setQuantity(quantity);
                    sale.setSoldPrice(item.getSellingPrice());
                    sale.setCustomer(customer);
                    brandRepo.save(brand);
                    return sale;
                })
                .toList();

        saleRepo.saveAll(sales);
    }

    public int getTodaySalesCount() {

       return getTodaySales().size();
    }
    public double getTodayRevenue() {

       return getTodaySales().stream().mapToDouble(Sale::getTotalAmount).sum();

    }

    public List<Sale> getTodaySales() {

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);
        return saleRepo.findBySaleDateBetween(startOfDay,endOfDay);
    }
    public long getTodayCustomers() {

        return getTodaySales().stream()
                .map(s -> s.getCustomer().getId())
                .distinct()
                .count();
    }

    public List<Sale> getSalesByDate(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23,59,59);

        return saleRepo.findBySaleDateBetween(start,end);
    }
}