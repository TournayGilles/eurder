package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.item.ressources.StockUrgency;
import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.item.dto.ItemDto;
import com.switchfully.eurder.service.item.dto.UpdateItemDto;
import com.switchfully.eurder.service.order.dto.ShippingItemPurchaseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper = new ItemMapper();

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public ItemDto addItem(String userId, CreateItemDto createItemDto) {
        userRepository.verifyAdmin(UUID.fromString(userId));
        return itemMapper.toItemDto(itemRepository.save(itemMapper.fromCreateItemDto(createItemDto)));
    }
    public List<ItemDto> getItemsByUrgency(String userId){
        userRepository.verifyAdmin(UUID.fromString(userId));
        return itemMapper.toItemDtoList(itemRepository.getItemsSortedByUrgency());
    }
    public List<ItemDto> getItemsWithSpecificUrgency(String userId, StockUrgency urgency){
        userRepository.verifyAdmin(UUID.fromString(userId));
        return itemMapper.toItemDtoList(itemRepository.getItemsForSpecificUrgency(urgency));
    }
    public ItemDto updateItem(String userId, String itemId, UpdateItemDto dto){
        userRepository.verifyAdmin(UUID.fromString(userId));
        Item item = itemRepository.getItemByUUId(UUID.fromString(itemId));
        item.updateItem(dto);
        return itemMapper.toItemDto(item);
    }
}
