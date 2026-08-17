package com.hamza.ecommerce_backend.category.DTO;

import com.hamza.ecommerce_backend.category.entity.CategoryStatus;

public class CategoryUpdateDTO {

    private String categoryName;
    private String description;
    private CategoryStatus status;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }
}
