package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.domain.order.ressources.ItemPurchase;
import com.switchfully.eurder.domain.order.ressources.Order;
import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.domain.user.ressources.Customer;
import com.switchfully.eurder.internals.exceptions.NoSuchOrderException;
import com.switchfully.eurder.service.order.dto.OrderDto;
import com.switchfully.eurder.service.order.dto.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper = new OrderMapper();

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public String addPurchase(String userId, String itemId, int amount) {
        Customer customer = userRepository.getCustomerById(UUID.fromString(userId));
        Item item = itemRepository.getItemByUUId(UUID.fromString(itemId));
        Order order;
        try {
            order = orderRepository.getOpenOrderByCustomerId(customer.getUserId());
        } catch (NoSuchOrderException exception){
            order = orderRepository.save(new Order(customer.getUserId()));
        }
        order.addItemPurchase(new ItemPurchase(item, amount));
        return order.getTotalPrice().toString();
    }

    public OrderDto checkout(String userId) {
        Customer customer = userRepository.getCustomerById(UUID.fromString(userId));
        Order order = orderRepository.getOpenOrderByCustomerId(customer.getUserId());
        order.closeOrder();
        return orderMapper.toOrderDto(order);

    }
}
