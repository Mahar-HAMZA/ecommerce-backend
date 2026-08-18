package com.hamza.ecommerce_backend.category.service;
import com.hamza.ecommerce_backend.category.DTO.CategoryCreateDTO;
import com.hamza.ecommerce_backend.category.DTO.CategoryDTO;
import com.hamza.ecommerce_backend.category.DTO.CategoryUpdateDTO;
import com.hamza.ecommerce_backend.category.exception.CategoryAlreadyExistsException;
import com.hamza.ecommerce_backend.category.mapper.CategoryMapper;
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

    private final CategoryMapper cateMapper;

    public CategoryService(CategoryRepository repo, CategoryMapper mapper){
        this.repo = repo;
        this.cateMapper=mapper;
    }

    public CategoryDTO createCategory(CategoryCreateDTO category){
        if(repo.existsByCategoryName(category.getCategoryName())){
            throw new CategoryAlreadyExistsException("Category already exists");
        }
        Category category1=cateMapper.toEntity(category);
            Category category2=repo.save(category1);
            return cateMapper.toDTO(category2);
    }

    public List<CategoryDTO> getAllCategories(){
        List<CategoryDTO> categoryList=cateMapper.allCategoriesToDTO(repo.findAll());
        return categoryList;
    }

    public CategoryDTO getCategoryById(Long id){
        Optional<Category> categoryOptional=repo.findById(id);
        if(categoryOptional.isPresent()){
            Category category1=categoryOptional.get();
            return cateMapper.toDTO(category1);
        }
        throw new RuntimeException("Category not found");
    }

    public CategoryDTO updateCategory(Long id, CategoryUpdateDTO category){
        Optional<Category> updateCategory=repo.findById(id);
        if(updateCategory.isPresent()){
            Category categoryUpdation=cateMapper.updateCategory(updateCategory.get(), category);
            repo.save(categoryUpdation);
            return cateMapper.toDTO(categoryUpdation);
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
