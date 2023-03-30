package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.domain.order.ressources.Order;
import com.switchfully.eurder.internals.exceptions.NoSuchOrderException;
import io.restassured.internal.common.assertion.AssertionSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderRepositoryTest {
    OrderRepository orderRepository;

    @BeforeEach
    void setup(){
        orderRepository = new OrderRepository();
    }

    @Test
    void Save_SaveOrderToRepo() {
        Order order = new Order(UUID.randomUUID());
        orderRepository.save(order);
        Assertions.assertEquals(order, orderRepository.getOrderById(order.getOrderId()));
    }
    @Test
    void getAllOrders_ReturnsListOfALlOrders(){
        orderRepository.save(new Order(UUID.randomUUID()));
        orderRepository.save(new Order(UUID.randomUUID()));

        Assertions.assertEquals(2, orderRepository.getAllOrders().size());
    }
    @Test
    void getOrderById_ThrowsExceptionWhenGivenUnknownId(){
        orderRepository.save(new Order(UUID.randomUUID()));
        Assertions.assertThrows(NoSuchOrderException.class, () -> orderRepository.getOrderById(UUID.randomUUID()));
    }
    @Test
    void getOrderById_ReturnsOrderOfGivenId(){
        Order order = new Order(UUID.randomUUID());
        orderRepository.save(order);
        Assertions.assertEquals(order, orderRepository.getOrderById(order.getOrderId()));
    }
    @Test
    void getOpenOrderByCustomerId_returnsOrderIfOneIsOpen(){
        UUID customerId = UUID.randomUUID();
        Order order = new Order(customerId);
        orderRepository.save(order);
        Assertions.assertEquals(order, orderRepository.getOpenOrderByCustomerId(customerId));
    }
    @Test
    void getOpenOrderByCustomerId_ThrowsException(){
        UUID customerId = UUID.randomUUID();
        Order order = new Order(customerId);
        orderRepository.save(order);
        order.closeOrder();
        Assertions.assertThrows(NoSuchOrderException.class, ()-> orderRepository.getOpenOrderByCustomerId(customerId));
        Assertions.assertThrows(NoSuchOrderException.class, ()-> orderRepository.getOpenOrderByCustomerId(UUID.randomUUID()));
    }
    @Test
    void getOrdersForCustomer_returnsListOfClosedOrders(){
        UUID customerId = UUID.randomUUID();
        Order order1 = new Order(customerId);
        Order order2 = new Order(customerId);
        orderRepository.save(order1);
        orderRepository.save(order2);
        Assertions.assertTrue(orderRepository.getOrdersForCustomer(customerId).isEmpty());
        order1.closeOrder();
        Assertions.assertTrue(orderRepository.getOrdersForCustomer(customerId).contains(order1));
        Assertions.assertFalse(orderRepository.getOrdersForCustomer(customerId).contains(order2));
    }
}
