package com.switchfully.eurder.service.order.dto;

import com.switchfully.eurder.domain.user.ressources.Address;
import com.switchfully.eurder.domain.user.ressources.Name;
import com.switchfully.eurder.service.item.dto.ItemDto;

import java.util.UUID;

public class ShippingItemPurchaseDto {
    public final UUID orderId;
    public final ItemDto itemdto;
    public final Name customerName;
    public final Address address;

    public ShippingItemPurchaseDto(UUID orderId, ItemDto itemdto, Name customerName, Address address) {
        this.orderId = orderId;
        this.itemdto = itemdto;
        this.customerName = customerName;
        this.address = address;
    }
}
