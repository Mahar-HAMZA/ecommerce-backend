package com.hamza.ecommerce_backend.order.service;

import com.hamza.ecommerce_backend.order.DTO.*;
import com.hamza.ecommerce_backend.order.entity.*;
import com.hamza.ecommerce_backend.order.exception.InsufficientStockException;
import com.hamza.ecommerce_backend.order.mapper.OrderMapper;
import com.hamza.ecommerce_backend.order.repository.*;
import com.hamza.ecommerce_backend.product.entity.Product;
import com.hamza.ecommerce_backend.product.exception.ProductNotFoundException;
import com.hamza.ecommerce_backend.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderMapper mapper;
    private OrderItemRepository OrderItemRepo;
    private OrderRepository OrderRepo;
    private ProductRepository productRepo;

    public OrderService(OrderMapper mapper, OrderItemRepository OrderItemRepo, OrderRepository OrderRepo, ProductRepository productRepo) {
        this.mapper=mapper;
        this.OrderItemRepo=OrderItemRepo;
        this.OrderRepo=OrderRepo;
        this.productRepo=productRepo;
    }

    @Transactional
    public OrderDTO makeOrder(OrderCreateDTO dto){
        List<OrderItem> orderItems=new ArrayList<>();
        Order order=mapper.toEntity(dto);
        BigDecimal totalAmount=BigDecimal.ZERO;
        for(OrderItemCreateDTO items : dto.getItems()){
            Optional<Product> product=productRepo.findById(items.getProductId());
            if(product.isPresent()){
                Product product1=product.get();
                if(product1.getStockQuantity() >= items.getQuantity()){
                    OrderItem item=new OrderItem();
                    item.setProduct(product1);
                    item.setQuantity(items.getQuantity());
                    item.setUnitPrice(product1.getPrice());
                    item.setOrder(order);
                    item.setSubtotal(product1.getPrice().multiply(BigDecimal.valueOf(items.getQuantity())));
                    orderItems.add(item);
                    totalAmount = totalAmount.add(item.getSubtotal());
                    product1.setStockQuantity(product1.getStockQuantity() - items.getQuantity());
                }
                else{
                    throw new InsufficientStockException("Insufficient stock available for the requested quantity");
                }
            }
            else{
                throw new ProductNotFoundException("Product does not exist");
            }
        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        Order savedOrder = OrderRepo.save(order);
        return mapper.toDTO(savedOrder);
    }

}
