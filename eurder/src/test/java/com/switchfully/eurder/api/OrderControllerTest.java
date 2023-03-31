package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.item.ressources.Price;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.domain.order.ressources.Order;
import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.domain.user.ressources.Address;
import com.switchfully.eurder.domain.user.ressources.Customer;
import com.switchfully.eurder.domain.user.ressources.Name;
import com.switchfully.eurder.service.order.OrderMapper;
import com.switchfully.eurder.service.order.dto.OrderReportDto;
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
public class OrderControllerTest {
    @LocalServerPort
    int port;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;
    Customer customer;
    Item item;
    OrderMapper orderMapper = new OrderMapper();
    @BeforeEach
    void setup(){
        customer = new Customer("aa@aa.com", new Address("a","a","a","a","a"),new Name("a","a"),"+32456789123");
        userRepository.save(customer);
        item =new Item("a","a",new Price(14.0,"Euro"),45);
        itemRepository.save(item);
    }
    @Test
    void addPurchase_returnsActualTotalPrice(){
        String price = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(new Header("userId",customer.getUserId().toString()))
                .body(3)
                .when()
                .port(port)
                .put("/order/" + item.getItemId().toString())
                .then()
                .assertThat()
                .statusCode(HttpStatus.ACCEPTED.value())
                .extract().asString();
        Assertions.assertEquals(new Price(14.0 * 3,"Euro").toString(),price);
        String price2 = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(new Header("userId",customer.getUserId().toString()))
                .body(3)
                .when()
                .port(port)
                .put("/order/" + item.getItemId().toString())
                .then()
                .assertThat()
                .statusCode(HttpStatus.ACCEPTED.value())
                .extract().asString();
        Assertions.assertEquals(new Price(14.0 * 6,"Euro").toString(),price2);
    }
    @Test
    void checkout_returnsTotalPriceAndCloseOrder(){
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(new Header("userId",customer.getUserId().toString()))
                .body(3)
                .when()
                .port(port)
                .put("/order/" + item.getItemId().toString())
                .then()
                .assertThat()
                .statusCode(HttpStatus.ACCEPTED.value());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(new Header("userId",customer.getUserId().toString()))
                .body(3)
                .when()
                .port(port)
                .put("/order/" + item.getItemId().toString())
                .then()
                .assertThat()
                .statusCode(HttpStatus.ACCEPTED.value());
        String price = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(new Header("userId",customer.getUserId().toString()))
                .when()
                .port(port)
                .patch("/order/checkout")
                .then()
                .assertThat()
                .statusCode(HttpStatus.ACCEPTED.value())
                .extract().asString();
        Assertions.assertEquals(new Price(14.0 * 6,"Euro").toString(),price);
        Assertions.assertFalse(orderRepository.getOrdersForCustomer(customer.getUserId()).get(0).isOpen());
    }
    @Test
    void Reorder_OrderSameAsBefore(){
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(new Header("userId",customer.getUserId().toString()))
                .body(3)
                .when()
                .port(port)
                .put("/order/" + item.getItemId().toString())
                .then()
                .assertThat()
                .statusCode(HttpStatus.ACCEPTED.value());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(new Header("userId",customer.getUserId().toString()))
                .when()
                .port(port)
                .patch("/order/checkout")
                .then()
                .assertThat()
                .statusCode(HttpStatus.ACCEPTED.value());
        Order order = orderRepository.getAllOrders().get(0);
        String price = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(new Header("userId",customer.getUserId().toString()))
                .when()
                .port(port)
                .post("/order/reorder/" + order.getOrderId().toString())
                .then()
                .assertThat()
                .statusCode(HttpStatus.ACCEPTED.value()).extract().asString();
        Assertions.assertEquals(order.getTotalPrice().toString(),price);
    }
    @Test
    void getOrdersForCustomer(){
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(new Header("userId",customer.getUserId().toString()))
                .body(3)
                .when()
                .port(port)
                .put("/order/" + item.getItemId().toString())
                .then()
                .assertThat()
                .statusCode(HttpStatus.ACCEPTED.value());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(new Header("userId",customer.getUserId().toString()))
                .when()
                .port(port)
                .patch("/order/checkout")
                .then()
                .assertThat()
                .statusCode(HttpStatus.ACCEPTED.value());
        Order order = orderRepository.getAllOrders().get(0);
        OrderReportDto report = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(new Header("userId", customer.getUserId().toString()))
                .when()
                .port(port)
                .get("/order")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().as(OrderReportDto.class);
        Assertions.assertEquals(orderMapper.generateOrderReport(orderRepository.getOrdersForCustomer(customer.getUserId())),report);
    }
}
