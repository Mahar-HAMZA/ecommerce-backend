package com.hamza.ecommerce_backend.order.mapper;

import com.hamza.ecommerce_backend.order.DTO.OrderCreateDTO;
import com.hamza.ecommerce_backend.order.DTO.OrderDTO;
import com.hamza.ecommerce_backend.order.DTO.OrderItemCreateDTO;
import com.hamza.ecommerce_backend.order.entity.Order;
import com.hamza.ecommerce_backend.order.entity.OrderItem;
import com.hamza.ecommerce_backend.order.entity.OrderStatus;

import java.time.LocalDateTime;

public class OrderMapper {

    public Order toEntity(OrderCreateDTO dto) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return order;
    }

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();

        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());

        return dto;
    }

}
