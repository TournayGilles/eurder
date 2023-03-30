package com.switchfully.eurder.service.order.dto;

public class ItemPurchaseDto {

    public final String itemName;
    public final int amount;
    public final String purchasePrice;

    public ItemPurchaseDto(String itemName ,int amount, String purchasePrice) {
        this.itemName = itemName;
        this.amount = amount;
        this.purchasePrice = purchasePrice;
    }
}
