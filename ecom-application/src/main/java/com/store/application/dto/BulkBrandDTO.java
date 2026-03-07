package com.store.application.dto;

import java.util.ArrayList;
import java.util.List;

public class BulkBrandDTO {

    private Long categoryId;
    private List<BrandDTO> brands = new ArrayList<>();

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<BrandDTO> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandDTO> brands) {
        this.brands = brands;
    }
}
