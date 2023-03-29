package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.item.ressources.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemRepositoryTest {
    private ItemRepository itemRepository = new ItemRepository();
    private Item item;
    @BeforeEach
    void setup(){
        item = new Item("chair", "a basic chair", new Price(Double.valueOf(17.56),"Euro"), 4);
    }
    @Test
    void save_addItemToRepo(){
        itemRepository.save(item);
        Assertions.assertTrue(itemRepository.getItemByUUIDRepository().containsValue(item));
    }
    @Test
    void save_ReturnItemSaved(){
        Assertions.assertEquals(item, itemRepository.save(item));
    }
}
