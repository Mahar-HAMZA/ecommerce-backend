package com.hamza.ecommerce_backend.order.mapper;

import com.hamza.ecommerce_backend.order.DTO.OrderCreateDTO;
import com.hamza.ecommerce_backend.order.DTO.OrderDTO;
import com.hamza.ecommerce_backend.order.DTO.OrderItemCreateDTO;
import com.hamza.ecommerce_backend.order.DTO.OrderItemDTO;
import com.hamza.ecommerce_backend.order.entity.Order;
import com.hamza.ecommerce_backend.order.entity.OrderItem;
import com.hamza.ecommerce_backend.order.entity.OrderStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
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

        List<OrderItemDTO> items = new ArrayList<>();

        for (OrderItem item : order.getOrderItems()) {
            OrderItemDTO itemDTO = new OrderItemDTO();

            itemDTO.setId(item.getId());
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setUnitPrice(item.getUnitPrice());
            itemDTO.setSubtotal(item.getSubtotal());

            items.add(itemDTO);
        }

        dto.setItems(items);

        return dto;
    }

}
