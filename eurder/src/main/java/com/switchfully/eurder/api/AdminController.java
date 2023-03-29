package com.switchfully.eurder.api;

import com.switchfully.eurder.service.item.ItemService;
import com.switchfully.eurder.service.User.UserService;
import com.switchfully.eurder.service.item.dto.CreateItemDto;
import com.switchfully.eurder.service.User.dto.CustomerDto;
import com.switchfully.eurder.service.item.dto.ItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private final UserService userService;
    private final ItemService itemService;

    public AdminController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
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
}
