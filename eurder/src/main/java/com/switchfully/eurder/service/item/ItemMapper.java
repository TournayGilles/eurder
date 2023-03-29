package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.item.dto.ItemDto;

public class ItemMapper {
    public Item fromCreateItemDto(CreateItemDto createItemDto){
        return new Item(createItemDto.itemName, createItemDto.itemDescription,
                createItemDto.price,
                createItemDto.stock);
    }
    public ItemDto toItemDto(Item item){
        return new ItemDto(item.getItemId(), item.getItemName(), item.getItemDescription(), item.getPrice(), item.getStock());
    }
}
