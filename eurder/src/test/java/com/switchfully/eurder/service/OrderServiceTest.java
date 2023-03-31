package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.item.ressources.Price;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.domain.order.ressources.Order;
import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.domain.user.ressources.Address;
import com.switchfully.eurder.domain.user.ressources.Customer;
import com.switchfully.eurder.domain.user.ressources.Name;
import com.switchfully.eurder.internals.exceptions.NoRightException;
import com.switchfully.eurder.internals.exceptions.NoSuchOrderException;
import com.switchfully.eurder.internals.exceptions.OrderStillOpenException;
import com.switchfully.eurder.service.order.OrderMapper;
import com.switchfully.eurder.service.order.OrderService;
import com.switchfully.eurder.service.order.dto.OrderDto;
import com.switchfully.eurder.service.order.dto.OrderReportDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceTest {
    OrderService orderService;
    OrderRepository orderRepository;
    ItemRepository itemRepository;
    UserRepository userRepository;
    OrderMapper orderMapper = new OrderMapper();
    String adminId;
    @BeforeEach
    void setup(){
        orderRepository = new OrderRepository();
        itemRepository = new ItemRepository();
        userRepository = new UserRepository();
        orderService = new OrderService(orderRepository,itemRepository,userRepository);
        adminId = userRepository.getUserByUUIDRepository().values().stream().toList().get(0).getUserId().toString();
    }
    @Test
    void addPurchase_CreatesNewPurchaseWhenUserHasNoneOpen(){
        Customer customer = new Customer("aa@aa.com",new Address("a","a","a","a","a"), new Name("a","a"),"+32475896412");
        Item item = new Item("b","b", new Price(17.3, "Euro"),4);
        userRepository.save(customer);
        itemRepository.save(item);
        Assertions.assertDoesNotThrow(() ->orderService.addPurchase(customer.getUserId().toString(),item.getItemId().toString(),2));
        Assertions.assertDoesNotThrow(()-> orderRepository.getOpenOrderByCustomerId(customer.getUserId()));
    }
    @Test
    void addPurchase_addPurchaseToOpenOrder(){
        Customer customer = new Customer("aa@aa.com",new Address("a","a","a","a","a"), new Name("a","a"),"+32475896412");
        Item item = new Item("b","b", new Price(17.3, "Euro"),4);
        Order order = new Order(customer.getUserId());

        userRepository.save(customer);
        itemRepository.save(item);
        orderRepository.save(order);
        orderService.addPurchase(customer.getUserId().toString(),item.getItemId().toString(), 1);
        Assertions.assertEquals(1, order.getPurchaseList().size());
    }
    @Test
    void checkout_ClosesOrderWhenOpenedOrderAndReturnsTotalPriceOfOrder(){
        Customer customer = new Customer("aa@aa.com",new Address("a","a","a","a","a"), new Name("a","a"),"+32475896412");
        Item item = new Item("b","b", new Price(17.3, "Euro"),4);
        Order order = new Order(customer.getUserId());

        userRepository.save(customer);
        itemRepository.save(item);
        orderRepository.save(order);
        orderService.addPurchase(customer.getUserId().toString(),item.getItemId().toString(), 3);
        orderService.addPurchase(customer.getUserId().toString(),item.getItemId().toString(), 3);
        Assertions.assertEquals(new Price(6 * 17.3,"Euro").toString(), orderService.checkout(customer.getUserId().toString()));
        Assertions.assertFalse(order.isOpen());
    }
    @Test
    void checkout_ThrowsExceptionWhenNoOpenOrderFound(){
        Customer customer = new Customer("aa@aa.com",new Address("a","a","a","a","a"), new Name("a","a"),"+32475896412");
        Order order = new Order(customer.getUserId());
        userRepository.save(customer);
        orderRepository.save(order);
        orderService.checkout(customer.getUserId().toString());
        Assertions.assertThrows(NoSuchOrderException.class,()-> orderService.checkout(customer.getUserId().toString()));
    }
    @Test
    void orderBasedOnOldOrder_ReturnsTotalPriceOfNewOrderIdenticalToOlderOrder(){
        Customer customer = new Customer("aa@aa.com",new Address("a","a","a","a","a"), new Name("a","a"),"+32475896412");
        Item item = new Item("b","b", new Price(17.3, "Euro"),4);
        Order order = new Order(customer.getUserId());

        userRepository.save(customer);
        itemRepository.save(item);
        orderRepository.save(order);
        orderService.addPurchase(customer.getUserId().toString(),item.getItemId().toString(), 3);
        orderService.addPurchase(customer.getUserId().toString(),item.getItemId().toString(), 3);
        orderService.checkout(customer.getUserId().toString());
        Assertions.assertEquals(order.getTotalPrice().toString(), orderService.orderBasedOnOldOrder(customer.getUserId().toString(), order.getOrderId().toString()));
    }
    @Test
    void orderBasedOnOldOrder_ExceptionThrowing(){
        Customer customer = new Customer("aa@aa.com",new Address("a","a","a","a","a"), new Name("a","a"),"+32475896412");
        Item item = new Item("b","b", new Price(17.3, "Euro"),4);
        Order order = new Order(customer.getUserId());

        userRepository.save(customer);
        itemRepository.save(item);
        orderRepository.save(order);
        Assertions.assertThrows(NoSuchOrderException.class, ()-> orderService.orderBasedOnOldOrder(customer.getUserId().toString(), UUID.randomUUID().toString()));
        Assertions.assertThrows(NoRightException.class,()-> orderService.orderBasedOnOldOrder(UUID.randomUUID().toString(),order.getOrderId().toString()));
        Assertions.assertThrows(OrderStillOpenException.class,()-> orderService.orderBasedOnOldOrder(customer.getUserId().toString(),order.getOrderId().toString()));
    }
    @Test
    void getOrderForCustomer_returnsOrderReportForCustomer(){
        Customer customer = new Customer("aa@aa.com",new Address("a","a","a","a","a"), new Name("a","a"),"+32475896412");
        Item item = new Item("b","b", new Price(17.3, "Euro"),4);
        Order order = new Order(customer.getUserId());
        userRepository.save(customer);
        itemRepository.save(item);
        orderRepository.save(order);
        order.closeOrder();
        orderService.orderBasedOnOldOrder(customer.getUserId().toString(),order.getOrderId().toString());
        OrderDto orderDto = orderMapper.toOrderDto(order);
        OrderReportDto orderReportDto = orderService.getOrdersForCustomer(customer.getUserId().toString());
        Assertions.assertTrue(orderReportDto.orders.contains(orderDto));
        Assertions.assertEquals(2, orderReportDto.orders.size());
    }

    @Test
    void getAllItemsShippingToday_returnsEmptyList(){
        Customer customer = new Customer("aa@aa.com",new Address("a","a","a","a","a"), new Name("a","a"),"+32475896412");
        Item item = new Item("b","b", new Price(17.3, "Euro"),4);
        Order order = new Order(customer.getUserId());
        userRepository.save(customer);
        itemRepository.save(item);
        orderRepository.save(order);
        orderService.addPurchase(customer.getUserId().toString(), item.getItemId().toString(), 1);
        order.closeOrder();
        Assertions.assertTrue(orderService.getAllItemsShippingToday(adminId).isEmpty());
    }
}
