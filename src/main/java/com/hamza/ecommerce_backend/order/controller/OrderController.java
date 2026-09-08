package com.hamza.ecommerce_backend.order.controller;

import com.hamza.ecommerce_backend.order.DTO.OrderCreateDTO;
import com.hamza.ecommerce_backend.order.DTO.OrderDTO;
import com.hamza.ecommerce_backend.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> makeOrder(@RequestBody @Valid OrderCreateDTO dto){
        OrderDTO response=orderService.makeOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}