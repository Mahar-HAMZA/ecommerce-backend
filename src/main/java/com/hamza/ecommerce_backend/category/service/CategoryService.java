package com.hamza.ecommerce_backend.category.service;
import com.hamza.ecommerce_backend.category.repository.CategoryRepository;
import com.hamza.ecommerce_backend.category.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    List<Category> cate=new ArrayList<>();

    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo){
        this.repo=repo;
    }

    public Category createCategory(Category category){
        if(repo.existsByCategoryName(category.getCategoryName())){
            throw new RuntimeException("Category already exists");
        }
            return repo.save(category);
    }

    public List<Category> getAllCategories(){
        return repo.findAll();
    }

    public Category getCategoryById(Long id){
        Optional<Category> categoryOptional=repo.findById(id);
        if(categoryOptional.isPresent()){
            return categoryOptional.get();
        }
        throw new RuntimeException("Category not found");
    }

    public Category updateCategory(Long id, Category category){
        Optional<Category> updateCategory=repo.findById(id);
        if(updateCategory.isPresent()){
            Category categoryUpdation=updateCategory.get();
            categoryUpdation.setCategoryName(category.getCategoryName());
            categoryUpdation.setDescription(category.getDescription());
            categoryUpdation.setStatus(category.getStatus());
            return repo.save(categoryUpdation);
        }
        throw new RuntimeException("Category not found");
    }

    public void deleteCategory(Long id){
        Optional<Category> category=repo.findById(id);
        if(category.isPresent()){
            repo.deleteById(id);
            return;
        }
        throw new RuntimeException("Category not found");
    }

}
