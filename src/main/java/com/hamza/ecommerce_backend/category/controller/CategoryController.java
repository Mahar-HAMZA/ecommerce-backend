package com.hamza.ecommerce_backend.category.controller;

import com.hamza.ecommerce_backend.category.DTO.CategoryCreateDTO;
import com.hamza.ecommerce_backend.category.DTO.CategoryDTO;
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
    public CategoryDTO createCategory(@RequestBody CategoryCreateDTO category){

        return service.createCategory(category);
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories(){
        return service.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id){
        return service.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category){
        return service.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        service.deleteCategory(id);
    }

}
