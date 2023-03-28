package com.switchfully.eurder.api;

import com.switchfully.eurder.service.User.UserService;
import com.switchfully.eurder.service.dto.CreateCustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerCustomer(@RequestBody CreateCustomerDto createCustomerDto){
        return userService.registerCustomer(createCustomerDto);
    }
}
