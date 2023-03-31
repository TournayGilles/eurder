package com.switchfully.eurder.service.order.dto;

import java.util.List;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(orderId, orderDto.orderId) && Objects.equals(itemPurchaseList, orderDto.itemPurchaseList) && Objects.equals(price, orderDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, itemPurchaseList, price);
    }
}
