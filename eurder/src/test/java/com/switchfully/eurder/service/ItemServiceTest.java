package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.item.ressources.Price;
import com.switchfully.eurder.domain.item.ressources.StockUrgency;
import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.internals.exceptions.NoRightException;
import com.switchfully.eurder.internals.exceptions.NoSuchItemFoundException;
import com.switchfully.eurder.service.item.ItemMapper;
import com.switchfully.eurder.service.item.ItemService;
import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.item.dto.ItemDto;
import com.switchfully.eurder.service.item.dto.UpdateItemDto;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemServiceTest {
    ItemRepository itemRepository = new ItemRepository();
    UserRepository userRepository = new UserRepository();
    ItemService itemService = new ItemService(itemRepository,userRepository);
    String adminId = userRepository.getUserByUUIDRepository().values().stream().findFirst().get().getUserId().toString();

    @Test
    void SaveItem_IfNotAdmin_ThrowsException(){
        Assertions.assertThrows(NoRightException.class,()-> itemService.addItem(UUID.randomUUID().toString(), null));
    }
    @Test
    void SaveItem_IfAdminAndValidObject_DoesNotThrowException(){
        Assertions.assertDoesNotThrow(() -> itemService.addItem(adminId, new CreateItemDto("a", "b", new Price(17.0,"euro"),4)));
    }
    @Test
    void getItemByUrgency_ThrowsExceptionWhenNotAdmin(){
        Assertions.assertThrows(NoRightException.class,()-> itemService.getItemsByUrgency(UUID.randomUUID().toString()));
    }
    @Test
    void getItemByUrgency_returnsOrderedListOfItemBasedOnUrgency(){
        Item item1 = new Item("aa",  "aa", new Price(17.5, "Euro"), 3);
        Item item2 = new Item("aa",  "aa", new Price(17.5, "Euro"), 13);
        Item item3 = new Item("aa",  "aa", new Price(17.5, "Euro"), 7);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        List<ItemDto> itemsByUrgency = itemService.getItemsByUrgency(adminId);
        ListAssert<ItemDto> itemDtoListAssert = ListAssert.assertThatList(itemsByUrgency);
        itemDtoListAssert.isSortedAccordingTo(Comparator.comparing(itemDto -> itemDto.urgency));
    }
    @Test
    void getItemForSpecificUrgency_containsOnlyItemsForThatUrgency(){
        Item item1 = new Item("aa",  "aa", new Price(17.5, "Euro"), 3);
        Item item2 = new Item("aa",  "aa", new Price(17.5, "Euro"), 13);
        Item item3 = new Item("aa",  "aa", new Price(17.5, "Euro"), 7);

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        Assertions.assertTrue(itemService.getItemsWithSpecificUrgency(adminId, StockUrgency.STOCK_LOW).stream().allMatch(itemDto -> itemDto.urgency == StockUrgency.STOCK_LOW));
    }
    @Test
    void getItemForSpecificUrgency_throwsException(){
        Assertions.assertThrows(NoRightException.class, ()-> itemService.getItemsWithSpecificUrgency(UUID.randomUUID().toString(), StockUrgency.STOCK_LOW));
    }
    @Test
    void updateItem_UpdateItemData(){
        Item item1 = new Item("aa",  "aa", new Price(17.5, "Euro"), 3);
        itemRepository.save(item1);
        UpdateItemDto updateItemDto =new UpdateItemDto("bb","bb", new Price(88.0,"Euro"), 5);
        ItemDto itemDto = itemService.updateItem(adminId,item1.getItemId().toString(), updateItemDto);
        Assertions.assertEquals(item1.getStock(), itemDto.stock );
        Assertions.assertEquals(item1.getItemName(), itemDto.itemName);
        Assertions.assertEquals(item1.getItemDescription(), itemDto.itemDescription);
        Assertions.assertEquals(item1.getPrice().toString(), itemDto.price);
    }
    @Test
    void updateItem_ExceptionThrowing(){
        Assertions.assertThrows(NoRightException.class, ()-> itemService.updateItem(UUID.randomUUID().toString(),UUID.randomUUID().toString(), null));
        Assertions.assertThrows(NoSuchItemFoundException.class, ()-> itemService.updateItem(adminId, UUID.randomUUID().toString(), null));
    }
}
