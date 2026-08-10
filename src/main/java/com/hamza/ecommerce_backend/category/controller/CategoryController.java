package com.hamza.ecommerce_backend.category.controller;

import com.hamza.ecommerce_backend.category.entity.Category;
import com.hamza.ecommerce_backend.category.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Category> getAllCategories(){
        return service.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id){
        return service.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category){
        return service.updateCategory(id, category);
    }

}
