package com.switchfully.eurder.service.item.dto;

import com.switchfully.eurder.domain.item.ressources.Price;

public class UpdateItemDto {
    public final String itemName;
    public final String itemDescription;
    public final Price price;
    public final int stock;

    public UpdateItemDto(String itemName, String itemDescription, Price price, int stock) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.stock = stock;
    }
}
