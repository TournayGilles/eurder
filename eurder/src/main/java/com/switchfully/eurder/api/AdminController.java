package com.switchfully.eurder.api;

import com.switchfully.eurder.service.User.UserService;
import com.switchfully.eurder.service.dto.CustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
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
}
