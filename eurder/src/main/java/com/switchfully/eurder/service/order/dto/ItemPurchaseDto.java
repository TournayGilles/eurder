package com.switchfully.eurder.service.order.dto;
import com.switchfully.eurder.service.item.dto.ItemDto;

import java.time.LocalDate;

public class ItemPurchaseDto {

    public final OrderItemDto item;
    public final int amount;
    public final LocalDate shippingDate;
    public final String purchasePrice;

    public ItemPurchaseDto(OrderItemDto item, int amount, LocalDate shippingDate, String purchasePrice) {
        this.item = item;
        this.amount = amount;
        this.shippingDate = shippingDate;
        this.purchasePrice = purchasePrice;
    }
}
