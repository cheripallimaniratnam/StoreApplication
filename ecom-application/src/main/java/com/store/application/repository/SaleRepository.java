package com.store.application.repository;

import com.store.application.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);
}
