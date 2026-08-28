package com.hamza.ecommerce_backend.category.DTO;

import com.hamza.ecommerce_backend.category.entity.CategoryStatus;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CategoryUpdateDTO {

    @Pattern(regexp = "^.*\\S.*$", message = "Category name must not be blank")
    @Size(max = 100, message = "Category name must not exceed 100 characters.")
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
