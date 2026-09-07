package com.hamza.ecommerce_backend.order.service;

import com.hamza.ecommerce_backend.order.DTO.OrderCreateDTO;
import com.hamza.ecommerce_backend.order.DTO.OrderDTO;
import com.hamza.ecommerce_backend.order.entity.Order;
import com.hamza.ecommerce_backend.order.mapper.OrderMapper;
import com.hamza.ecommerce_backend.order.repository.OrderItemRepository;
import com.hamza.ecommerce_backend.order.repository.OrderRepository;
import com.hamza.ecommerce_backend.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

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

    public OrderDTO makeOrder(OrderCreateDTO dto){
        Order order=mapper.toEntity(dto);
        Order order1=OrderRepo.save(order);
        return mapper.toDTO(order1);
    }

}
