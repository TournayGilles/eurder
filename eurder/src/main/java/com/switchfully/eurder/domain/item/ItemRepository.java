package com.switchfully.eurder.domain.item;

import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.internals.exceptions.NoSuchItemFoundException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {
    private final Map<UUID, Item> itemByUUIDRepository;

    public ItemRepository() {
        itemByUUIDRepository = new ConcurrentHashMap<>();
    }

    public Item save(Item item){
        itemByUUIDRepository.put(item.getItemId(), item);
        return item;
    }
    public Item getItemByUUId(UUID itemId){
        Item item = itemByUUIDRepository.get(itemId);
        if (item == null){
            throw new NoSuchItemFoundException();
        }
        return item;
    }
    public Map<UUID, Item> getItemByUUIDRepository() {
        return itemByUUIDRepository;
    }
}
