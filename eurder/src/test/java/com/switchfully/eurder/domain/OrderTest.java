package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.item.ressources.Price;
import com.switchfully.eurder.domain.order.ressources.ItemPurchase;
import com.switchfully.eurder.domain.order.ressources.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderTest {
    ItemPurchase itemPurchase;
    Order order;
    @BeforeEach
    void setup(){
        itemPurchase = new ItemPurchase(new Item("a","a", new Price(17.5, "Euro"),7),4);
        order = new Order(UUID.randomUUID());
    }
    @Test
    void addPurchase_addsPurchaseToPurchaseList(){
        order.addItemPurchase(itemPurchase);
        Assertions.assertTrue(order.getPurchaseList().contains(itemPurchase));
    }
    @Test
    void getTotalPrice_ReturnsTotalPriceOfAllPurchases(){
        ItemPurchase itemPurchase2 = new ItemPurchase(new Item("desk","a",new Price(5.3, "Euro"), 5),3);
        order.addItemPurchase(itemPurchase);
        order.addItemPurchase(itemPurchase2);
        Assertions.assertEquals(new Price((17.5 * 4) + (5.3 * 3),"Euro"), order.getTotalPrice());
    }
}
