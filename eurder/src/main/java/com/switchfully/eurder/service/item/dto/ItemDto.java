package com.switchfully.eurder.service.item.dto;

import com.switchfully.eurder.domain.item.ressources.Price;
import com.switchfully.eurder.domain.item.ressources.StockUrgency;

import java.util.UUID;

public class ItemDto {
    public final UUID itemId;
    public final String itemName;
    public final String itemDescription;
    public final String price;
    public final int stock;
    public final StockUrgency urgency;

    public ItemDto(UUID itemId, String itemName, String itemDescription, Price price, int stock) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price.toString();
        this.stock = stock;
        urgency = StockUrgency.setStockUrgency(stock);
    }
}
