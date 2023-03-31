package com.switchfully.eurder.service.order.dto;

import java.util.Objects;

public class ItemPurchaseDto {

    public final String itemName;
    public final int amount;
    public final String purchasePrice;

    public ItemPurchaseDto(String itemName ,int amount, String purchasePrice) {
        this.itemName = itemName;
        this.amount = amount;
        this.purchasePrice = purchasePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPurchaseDto that = (ItemPurchaseDto) o;
        return amount == that.amount && Objects.equals(itemName, that.itemName) && Objects.equals(purchasePrice, that.purchasePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, amount, purchasePrice);
    }
}
