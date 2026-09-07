package com.hamza.ecommerce_backend.order.DTO;

import java.util.List;

public class OrderCreateDTO {

    private List<OrderItemCreateDTO> items;

    public List<OrderItemCreateDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemCreateDTO> items) {
        this.items = items;
    }
}
