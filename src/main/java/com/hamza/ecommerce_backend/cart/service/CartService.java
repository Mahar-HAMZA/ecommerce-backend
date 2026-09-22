package com.hamza.ecommerce_backend.cart.service;

import com.hamza.ecommerce_backend.cart.DTO.AddCartDTO;
import com.hamza.ecommerce_backend.cart.entity.Cart;
import com.hamza.ecommerce_backend.cart.repository.CartRepository;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.exception.ProductNotFoundException;
import com.hamza.ecommerce_backend.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class CartService {

    @Autowired
    private CartRepository CartRepo;

    ProductRepository ProductRepo;

    public CartService(CartRepository CartRepo,  ProductRepository ProductRepo) {
        this.CartRepo = CartRepo;
        this.ProductRepo = ProductRepo;
    }

//    public Cart addToCart(AddCartDTO dto){
//        Optional<Product> product=ProductRepo.findById(dto.getProductId());
//
//        if(!product.isPresent()){
//            throw new ProductNotFoundException("Product does not exist");
//        }
//        Product existProduct=product.get();
//        if(dto.getQuantity() < existProduct.getStockQuantity()){
//            if(CartRepo.)
//        }
//    }

}
