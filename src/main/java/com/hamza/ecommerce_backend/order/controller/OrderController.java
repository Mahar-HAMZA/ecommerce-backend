package com.hamza.ecommerce_backend.order.controller;

import com.hamza.ecommerce_backend.order.DTO.OrderCreateDTO;
import com.hamza.ecommerce_backend.order.DTO.OrderDTO;
import com.hamza.ecommerce_backend.order.DTO.OrderStatusUpdateDTO;
import com.hamza.ecommerce_backend.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }

    @GetMapping
    public List<OrderDTO> getAllOrders(){
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrderStatus(@PathVariable Long id, @RequestBody @Valid OrderStatusUpdateDTO dto){
        return orderService.updateOrderStatus(id, dto);
    }

}