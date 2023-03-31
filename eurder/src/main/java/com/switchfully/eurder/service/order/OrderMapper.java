package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.item.ressources.Price;
import com.switchfully.eurder.domain.order.ressources.ItemPurchase;
import com.switchfully.eurder.domain.order.ressources.Order;
import com.switchfully.eurder.service.order.dto.ItemPurchaseDto;
import com.switchfully.eurder.service.order.dto.OrderDto;
import com.switchfully.eurder.service.order.dto.OrderReportDto;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public ItemPurchaseDto toItemPurchaseDto(ItemPurchase itemPurchase){
        return new ItemPurchaseDto(itemPurchase.getItem().getItemName(), itemPurchase.getAmount(),itemPurchase.getPurchasePrice().toString());
    }
    public OrderDto toOrderDto(Order order){
        List<ItemPurchaseDto> list = order.getPurchaseList().stream().map(this::toItemPurchaseDto).toList();
        return new OrderDto(order.getOrderId(),list,order.getTotalPrice().toString());
    }
    public List<OrderDto> toOrderDtoList(List<Order> orders){
        return orders.stream().map(this::toOrderDto).collect(Collectors.toList());
    }
    public OrderReportDto generateOrderReport(List<Order> orders){
        Price totalPrice = orders.stream().map(Order::getTotalPrice).reduce(Price::addPrices).orElse(new Price(0.0, "Euro"));
        return new OrderReportDto(toOrderDtoList(orders),totalPrice.toString());
    }
}
