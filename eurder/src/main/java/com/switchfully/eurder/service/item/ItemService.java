package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.item.dto.ItemDto;
import org.springframework.stereotype.Service;

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
}
