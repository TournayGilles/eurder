package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.item.ressources.Item;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.domain.order.ressources.ItemPurchase;
import com.switchfully.eurder.domain.order.ressources.Order;
import com.switchfully.eurder.domain.user.UserRepository;
import com.switchfully.eurder.domain.user.ressources.Customer;
import com.switchfully.eurder.internals.exceptions.NoRightException;
import com.switchfully.eurder.internals.exceptions.NoSuchOrderException;
import com.switchfully.eurder.internals.exceptions.OrderStillOpenException;
import com.switchfully.eurder.service.item.ItemMapper;
import com.switchfully.eurder.service.order.dto.OrderReportDto;
import com.switchfully.eurder.service.order.dto.ShippingItemPurchaseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper = new OrderMapper();
    private final ItemMapper itemMapper = new ItemMapper();

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

    public String checkout(String userId) {
        Customer customer = userRepository.getCustomerById(UUID.fromString(userId));
        Order order = orderRepository.getOpenOrderByCustomerId(customer.getUserId());
        order.closeOrder();
        return order.getTotalPrice().toString();

    }

    public String orderBasedOnOldOrder(String userId, String orderId) {
        Order order = orderRepository.getOrderById(UUID.fromString(orderId));
        if (!UUID.fromString(userId).equals(order.getCustomerId())){
            throw new NoRightException();
        }
        else if (order.isOpen()){
            throw new OrderStillOpenException();
        }
        return createNewOrderBasedOnOldOne(order).getTotalPrice().toString();
    }
    private Order createNewOrderBasedOnOldOne(Order order) {
        Order newOrder = new Order(order.getCustomerId());
        for (ItemPurchase purchase: order.getPurchaseList()){
            newOrder.addItemPurchase(new ItemPurchase(itemRepository.getItemByUUId(purchase.getItem().getItemId()), purchase.getAmount()));
        }
        orderRepository.save(newOrder);
        newOrder.closeOrder();
        return newOrder;
    }

    public OrderReportDto getOrdersForCustomer(String userId) {
       return orderMapper.generateOrderReport(orderRepository.getOrdersForCustomer(UUID.fromString(userId)));
    }
    public List<ShippingItemPurchaseDto> getAllItemsShippingToday(String userId) {
        userRepository.verifyAdmin(UUID.fromString(userId));
        List<Order> orderList = orderRepository.getAllOrders();
        return generateShippingItemList(orderList);
    }

    private List<ShippingItemPurchaseDto> generateShippingItemList(List<Order> orderList) {
        List<ItemPurchase> purchaseList;
        Customer customer;
        List<ShippingItemPurchaseDto> shippingItemPurchaseDtos = new ArrayList<>();
        for (Order order: orderList){
            purchaseList = order.getPurchaseList().stream()
                    .filter(purchase -> purchase.getShippingDate().atStartOfDay().equals(LocalDate.now().atStartOfDay()))
                    .toList();
            customer = userRepository.getCustomerById(order.getCustomerId());
            for (ItemPurchase purchase:purchaseList){
                shippingItemPurchaseDtos.add(new ShippingItemPurchaseDto(order.getOrderId(),itemMapper.toItemDto(purchase.getItem()),customer.getName(),customer.getAddress(), purchase.getAmount()));
            }
        }
        return shippingItemPurchaseDtos;
    }
}
