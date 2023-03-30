package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.item.ressources.StockUrgency;
import com.switchfully.eurder.service.item.ItemService;
import com.switchfully.eurder.service.User.UserService;
import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.User.dto.CustomerDto;
import com.switchfully.eurder.service.item.dto.ItemDto;
import com.switchfully.eurder.service.item.dto.UpdateItemDto;
import com.switchfully.eurder.service.order.OrderService;
import com.switchfully.eurder.service.order.dto.ShippingItemPurchaseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private final UserService userService;
    private final ItemService itemService;

    private final OrderService orderService;

    public AdminController(UserService userService, ItemService itemService, OrderService orderService) {
        this.userService = userService;
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @GetMapping(path = "/customers", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllCustomers(@RequestHeader String userId){
        return userService.getAllCustomers(userId);
    }
    @GetMapping(path = "/customers/{customerId}", produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public CustomerDto getCustomerById(@RequestHeader String userId, @PathVariable String customerId){
        return userService.getCustomerById(userId,customerId);
    }
    @PostMapping(path = "/items/add", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addItem(@RequestHeader String userId, @RequestBody CreateItemDto createItemDto){
        return itemService.addItem(userId, createItemDto);
    }
    @GetMapping(path = "/items", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getAllItems(@RequestHeader String userId){
        return itemService.getItemsByUrgency(userId);
    }
    @GetMapping(path = "/items/urgency", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getItemsForSpecificUrgency(@RequestHeader String userId, @RequestBody StockUrgency stockUrgency){
        return itemService.getItemsWithSpecificUrgency(userId,stockUrgency);
    }
    @PatchMapping(path = "/items/update/{itemId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ItemDto updateItem(@RequestHeader String userId, @PathVariable String itemId, @RequestBody UpdateItemDto dto){
        return itemService.updateItem(userId,itemId,dto);
    }
    @GetMapping(path = "/orders/shipping", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ShippingItemPurchaseDto> getAllItemsShippingToday(@RequestHeader String userId){
        return orderService.getAllItemsShippingToday(userId);
    }


}
