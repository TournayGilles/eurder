package com.switchfully.eurder.domain.item.ressources;

import java.util.UUID;

public class Item {
    private final UUID itemId;
    private String itemName;
    private String itemDescription;
    private Price price;
    private int stock;

    public Item(String itemName, String itemDescription, Price price, int stock) {
        itemId = UUID.randomUUID();
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.stock = stock;
    }
    public Item(Item item){
        itemId = item.getItemId();
        itemName = item.getItemName();
        itemDescription= item.getItemDescription();
        price = item.getPrice();
        stock = item.getStock();
    }

    public UUID getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public Price getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void updateStock(int amount) {
        stock += amount;
    }
}
