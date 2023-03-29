package com.switchfully.eurder.service.order.dto;

import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.order.ressources.ItemPurchase;
import com.switchfully.eurder.domain.order.ressources.Order;

import java.util.List;

public class OrderMapper {

    public OrderItemDto toOrderItemDto(Item item){
        return new OrderItemDto(item.getItemId(),item.getItemName(), item.getItemDescription(), item.getPrice().toString());
    }
    public ItemPurchaseDto toItemPurchaseDto(ItemPurchase itemPurchase){
        return new ItemPurchaseDto(toOrderItemDto(itemPurchase.getItem()), itemPurchase.getAmount(), itemPurchase.getShippingDate(),itemPurchase.getPurchasePrice().toString());
    }
    public OrderDto toOrderDto(Order order){
        List<ItemPurchaseDto> list = order.getPurchaseList().stream().map(purchase -> toItemPurchaseDto(purchase)).toList();
        return new OrderDto(order.getOrderId(),list,order.getTotalPrice().toString());
    }
}
