package com.switchfully.eurder.domain.order.ressources;
import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.item.ressources.Price;

import java.time.LocalDate;


public class ItemPurchase {
    private final Item item;
    private final int amount;
    private final LocalDate shippingDate;
    private final Price purchasePrice;

    public ItemPurchase(Item item, int amount) {
        this.item = new Item(item);
        this.amount = amount;
        if (amount > item.getStock())
            shippingDate = LocalDate.now().plusDays(7);
        else {
            item.updateStock(-amount);
            shippingDate = LocalDate.now().plusDays(1);
        }
        purchasePrice = item.getPrice().calculatePrice(amount);
    }

    public Price getPurchasePrice() {
        return purchasePrice;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }
}
