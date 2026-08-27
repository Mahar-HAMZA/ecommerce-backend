package com.hamza.ecommerce_backend.category.controller;

import com.hamza.ecommerce_backend.category.DTO.CategoryCreateDTO;
import com.hamza.ecommerce_backend.category.DTO.CategoryDTO;
import com.hamza.ecommerce_backend.category.DTO.CategoryUpdateDTO;
import com.hamza.ecommerce_backend.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public CategoryDTO createCategory(@RequestBody @Valid CategoryCreateDTO category){

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
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateDTO updateCategory){
        return service.updateCategory(id, updateCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
