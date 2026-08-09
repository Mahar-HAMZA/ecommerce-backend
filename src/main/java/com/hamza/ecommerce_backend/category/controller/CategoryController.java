package com.hamza.ecommerce_backend.category.controller;

import com.hamza.ecommerce_backend.category.entity.Category;
import com.hamza.ecommerce_backend.category.service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;
    public CategoryController(CategoryService service){
        this.service=service;
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category){
        return service.createCategory(category);
    }

}
