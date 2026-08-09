package com.hamza.ecommerce_backend.category.repository;


import com.hamza.ecommerce_backend.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public  boolean existsByCategoryName(String categoryName);

}
