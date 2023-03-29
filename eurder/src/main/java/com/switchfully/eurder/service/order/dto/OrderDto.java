package com.switchfully.eurder.service.order.dto;

import java.util.List;
import java.util.UUID;

public class OrderDto {
    public final UUID orderId;
    public final List<ItemPurchaseDto> itemPurchaseList;

    public final String price;

    public OrderDto(UUID orderId, List<ItemPurchaseDto> itemPurchaseList, String price) {
        this.orderId = orderId;
        this.itemPurchaseList = itemPurchaseList;
        this.price = price;
    }
}
