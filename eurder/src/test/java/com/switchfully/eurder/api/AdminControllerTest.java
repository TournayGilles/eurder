package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.item.ressources.Price;
import com.switchfully.eurder.domain.item.ressources.StockUrgency;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.domain.order.ressources.Order;
import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.domain.user.ressources.Address;
import com.switchfully.eurder.domain.user.ressources.Customer;
import com.switchfully.eurder.domain.user.ressources.Name;
import com.switchfully.eurder.service.User.dto.CustomerDto;
import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.item.dto.ItemDto;
import com.switchfully.eurder.service.item.dto.UpdateItemDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    String adminId;
    Header header;
    @BeforeEach
    void setup(){
        adminId = userRepository.getUserByUUIDRepository().values().stream().toList().get(0).getUserId().toString();
        header = new Header("userId",adminId);
    }
    @Test
    void getAllCustomers_ReturnsListOfCustomerDtos(){
        userRepository.save(new Customer("aa@aa.com", new Address("a","a","a","a","a"),new Name("a","a"),"+32456789123"));
        userRepository.save(new Customer("aa@aa.com", new Address("a","a","a","a","a"),new Name("a","a"),"+32456789123"));
        List<CustomerDto> list = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(header)
                .when()
                .port(port)
                .get("/admin/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", CustomerDto.class);
        Assertions.assertEquals(2,list.size());
    }
    @Test
    void getCustomerById_ReturnsCustomer() {
        Customer customer = new Customer("aa@aa.com", new Address("a", "a", "a", "a", "a"), new Name("a", "a"), "+32456789123");
        userRepository.save(customer);
        CustomerDto customerDto = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(header)
                .when()
                .port(port)
                .get("/admin/customers/" + customer.getUserId().toString())
                .then()
                .assertThat()
                .statusCode(HttpStatus.FOUND.value())
                .extract().as(CustomerDto.class);
        Assertions.assertEquals(customerDto.userId, customer.getUserId());
    }
    @Test
    void addItem_addITemToItemRepo(){
        CreateItemDto createItemDto = new CreateItemDto("a","a",new Price(17.0,"Euro"),78);

        ItemDto itemDto = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(header)
                .body(createItemDto)
                .when()
                .port(port)
                .post("/admin/items/add")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(ItemDto.class);
        Assertions.assertTrue(itemRepository.getItemByUUIDRepository().containsKey(itemDto.itemId));
        Assertions.assertEquals(createItemDto.stock, itemDto.stock);
        Assertions.assertEquals(createItemDto.itemDescription,itemDto.itemDescription);
        Assertions.assertEquals(createItemDto.price.toString(),itemDto.price);
        Assertions.assertEquals(createItemDto.itemName, itemDto.itemName);
    }
    @Test
    void getAllItems_returnsListOfItemDto(){
        itemRepository.save(new Item("a","a",new Price(14.0,"Euro"),45));
        itemRepository.save(new Item("b","b",new Price(7.55,"Euro"),7));
        List<ItemDto> list = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(header)
                .when()
                .port(port)
                .get("/admin/items")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", ItemDto.class);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(list.get(0).urgency, StockUrgency.STOCK_MEDIUM);
    }
    @Test
    void updateItem(){
        Item item =new Item("a","a",new Price(14.0,"Euro"),45);
        UpdateItemDto updateItemDto = new UpdateItemDto("b","b",new Price(15.0,"Euro"),2);
        itemRepository.save(item);
        ItemDto itemDto = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(header)
                .body(updateItemDto)
                .when()
                .port(port)
                .patch("/admin/items/update/"+ item.getItemId().toString())
                .then()
                .assertThat()
                .statusCode(HttpStatus.ACCEPTED.value())
                .extract().as(ItemDto.class);
        Assertions.assertEquals(item.getItemName(),itemDto.itemName);
        Assertions.assertEquals(item.getItemDescription(),itemDto.itemDescription);
        Assertions.assertEquals(item.getPrice().toString(),itemDto.price);
        Assertions.assertEquals(item.getStock(),itemDto.stock);
    }
    @Test
    void ValidateGetShipping(){
        Item item =new Item("a","a",new Price(14.0,"Euro"),45);
        Customer customer = new Customer("aa@aa.com", new Address("a","a","a","a","a"),new Name("a","a"),"+32456789123");
        itemRepository.save(item);
        userRepository.save(customer);
        Order order = new Order(customer.getUserId());
        orderRepository.save(order);
        order.closeOrder();
        RestAssured.given().contentType(ContentType.JSON)
                .header(header).when().port(port).get("/admin/orders/shipping").then().assertThat().statusCode(HttpStatus.OK.value());
    }
}
