package com.store.application.service;

import com.store.application.dto.BulkBrandDTO;
import com.store.application.exception.CategoryNotFoundException;
import com.store.application.exception.InvalidBulkBrandException;
import com.store.application.model.Brand;
import com.store.application.model.Category;
import com.store.application.repository.BrandRepository;
import com.store.application.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    public void saveBrand(BulkBrandDTO bulkBrand) {

        if (bulkBrand.getCategoryId() == null) {
            throw new InvalidBulkBrandException("Please select a category.");
        }

        Category category = categoryRepo
                .findById(bulkBrand.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException("Selected category not found."));

        if (bulkBrand.getBrands() == null || bulkBrand.getBrands().isEmpty()) {
            throw new InvalidBulkBrandException("Please add at least one brand.");
        }

        List<Brand> brandList = bulkBrand.getBrands()
                .stream()
                // remove empty names
                .filter(dto -> dto.getName() != null && !dto.getName().isBlank())

                // validate price & stock
                .peek(dto -> {
                    if (dto.getPrice() < 0) {
                        throw new InvalidBulkBrandException(
                                "Price cannot be negative for brand: " + dto.getName());
                    }
                    if (dto.getStockCount() < 0) {
                        throw new InvalidBulkBrandException(
                                "Stock cannot be negative for brand: " + dto.getName());
                    }
                })
                // convert DTO → Entity
                .map(dto -> {
                    Brand brand = new Brand();
                    brand.setName(dto.getName());
                    brand.setPrice(dto.getPrice());
                    brand.setStockCount(dto.getStockCount());
                    brand.setCategory(category);
                    return brand;
                })
                .toList(); // Java 16+

        if (brandList.isEmpty()) {
            throw new InvalidBulkBrandException("Please add at least one valid brand.");
        }

        brandRepo.saveAll(brandList);
    }

    public List<Brand> getAllProducts() {
        return brandRepo.findAll();
    }

    public int getTotalStock() {
        return brandRepo.findAll()
                .stream()
                .mapToInt(Brand::getStockCount)
                .sum();
    }
}