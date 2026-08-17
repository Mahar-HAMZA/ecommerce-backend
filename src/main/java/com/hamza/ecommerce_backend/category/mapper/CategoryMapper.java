package com.hamza.ecommerce_backend.category.mapper;

import com.hamza.ecommerce_backend.category.DTO.CategoryCreateDTO;
import com.hamza.ecommerce_backend.category.DTO.CategoryDTO;
import com.hamza.ecommerce_backend.category.DTO.CategoryUpdateDTO;
import com.hamza.ecommerce_backend.category.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category){
        CategoryDTO dto =new CategoryDTO();
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        dto.setStatus(category.getStatus());
        return dto;
    }

    public List<CategoryDTO> allCategoriesToDTO(List<Category> category1){
        List<CategoryDTO> categoryDTOList=new ArrayList<>();
        for(Category category: category1){
            categoryDTOList.add(toDTO(category));
        }
        return categoryDTOList;
    }

    public Category toEntity(CategoryCreateDTO dto) {
        Category category=new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setStatus(dto.getStatus());
        return category;
    }

    public Category updateCategory(Category category, CategoryUpdateDTO updateDto){
        category.setCategoryName(updateDto.getCategoryName());
        category.setDescription(updateDto.getDescription());
        category.setStatus(updateDto.getStatus());
        return category;
    }
}
