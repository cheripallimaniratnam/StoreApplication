package com.store.application.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@EntityListeners(AuditingEntityListener.class)
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime saleDate;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private double soldPrice;

    @Column
    private double totalAmount;

    @ManyToOne
    private Customer customer;

    public void setTotalAmount() {
        this.totalAmount = quantity * soldPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", saleDate=" + saleDate +
                ", brand=" + brand +
                ", soldPrice=" + soldPrice +
                ", totalAmount=" + totalAmount +
                ", customer=" + customer +
                '}';
    }

}