package com.switchfully.eurder.domain.order.ressources;

import com.switchfully.eurder.domain.item.ressources.Price;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private final List<ItemPurchase> purchaseList;
    private final UUID customerId;
    private boolean isOpen;

    public Order(UUID customerId) {
        orderId = UUID.randomUUID();
        purchaseList = new ArrayList<>();
        this.customerId = customerId;
        isOpen = true;
    }
    public void addItemPurchase(ItemPurchase itemPurchase){
        purchaseList.add(itemPurchase);
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Price getTotalPrice() {
        return purchaseList.stream().map(purchase -> purchase.getPurchasePrice()).reduce(Price::addPrices).orElse(null);
    }

    public List<ItemPurchase> getPurchaseList() {
        return purchaseList;
    }

    public UUID getCustomerId() {
        return customerId;
    }
    public boolean isOpen(){
        return isOpen;
    }
    public void closeOrder(){
        isOpen = false;
    }
}
