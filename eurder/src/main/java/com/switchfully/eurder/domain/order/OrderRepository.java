package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.order.ressources.ItemPurchase;
import com.switchfully.eurder.domain.order.ressources.Order;
import com.switchfully.eurder.internals.exceptions.NoSuchOrderException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {
    private final Map<UUID, Order> orderByUUIDRepository;

    public OrderRepository() {
        this.orderByUUIDRepository = new ConcurrentHashMap<>();
    }
    public Order save(Order order){
        orderByUUIDRepository.put(order.getOrderId(),order);
        return order;
    }
    public List<Order> getAllOrders(){
        return orderByUUIDRepository.values().stream().toList();
    }
    public Order getOrderById(UUID orderId){
        Order order = orderByUUIDRepository.get(orderId);
        if (order == null){
            throw new NoSuchOrderException();
        }
        return order;
    }
    public Order getOpenOrderByCustomerId(UUID customerId){
        Order order = orderByUUIDRepository.values().stream().filter(ord -> ord.isOpen() && ord.getCustomerId().equals(customerId)).findFirst().orElse(null);
        if (order == null){
            throw new NoSuchOrderException();
        }
        return order;
    }
    public List<Order> getOrdersForCustomer(UUID customerId){
        return orderByUUIDRepository.values()
                .stream()
                .filter(order -> order.getCustomerId().equals(customerId) && !order.isOpen())
                .toList();
    }
}
