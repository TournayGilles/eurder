package com.switchfully.eurder.service.item.dto;

import com.switchfully.eurder.domain.item.ressources.Price;
import com.switchfully.eurder.domain.item.ressources.StockUrgency;

import java.util.UUID;

public class ItemDto {
    public UUID itemId;
    public String itemName;
    public String itemDescription;
    public String price;
    public int stock;
    public StockUrgency urgency;

    public ItemDto(UUID itemId, String itemName, String itemDescription, Price price, int stock) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price.toString();
        this.stock = stock;
        urgency = StockUrgency.setStockUrgency(stock);
    }

    public ItemDto(UUID itemId, String itemName, String itemDescription, String price, int stock, StockUrgency urgency) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.stock = stock;
        this.urgency = urgency;
    }

    public ItemDto() {
    }
}
