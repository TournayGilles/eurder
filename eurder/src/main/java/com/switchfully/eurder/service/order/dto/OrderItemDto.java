package com.switchfully.eurder.service.order.dto;

import java.util.UUID;

public class OrderItemDto {
    public final UUID itemId;
    public final String itemName;
    public final String itemDescription;
    public final String price;

    public OrderItemDto(UUID itemId, String itemName, String itemDescription, String price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
    }
}
