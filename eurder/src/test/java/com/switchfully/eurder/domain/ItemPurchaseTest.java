package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.item.ressources.Price;
import com.switchfully.eurder.domain.order.ressources.ItemPurchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemPurchaseTest {
    @Test
    void whenCreatingPurchase_AllValuesAreCorrectlySet(){
        Item item = new Item("chair", "a chair", new Price(72.5, "Euro"), 5);
        ItemPurchase itemPurchase = new ItemPurchase(item, 7);
        Assertions.assertEquals(itemPurchase.getItem().getItemId(), item.getItemId());
        Assertions.assertEquals(itemPurchase.getPurchasePrice(), item.getPrice().calculatePrice(7));
        Assertions.assertEquals(item.getStock(), 5);
        Assertions.assertEquals(LocalDate.now().plusDays(7), itemPurchase.getShippingDate());
        itemPurchase = new ItemPurchase(item, 1);
        Assertions.assertEquals(LocalDate.now().plusDays(1), itemPurchase.getShippingDate());
    }
}
