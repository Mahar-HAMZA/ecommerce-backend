package com.hamza.ecommerce_backend.order.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class OrderCreateDTO {

    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemCreateDTO> items;

    public List<OrderItemCreateDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemCreateDTO> items) {
        this.items = items;
    }
}
