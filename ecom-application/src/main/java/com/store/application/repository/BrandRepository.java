package com.store.application.repository;

import com.store.application.model.Brand;
import com.store.application.model.Category;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Brand> findById(Long id);
    List<Brand> findByCategoryId(Long id);
    Optional<Brand> findByNameIgnoreCaseAndCategoryId(String name, Long categoryId);

    boolean existsByCategory(Category category);
}

