package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.item.ressources.Price;
import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.internals.exceptions.NoRightException;
import com.switchfully.eurder.service.item.ItemService;
import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.item.dto.ItemDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
}
