package com.hamza.ecommerce_backend.category.service;
import com.hamza.ecommerce_backend.category.repository.CategoryRepository;
import com.hamza.ecommerce_backend.category.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

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

}
