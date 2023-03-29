package com.switchfully.eurder.api;

import com.switchfully.eurder.service.order.OrderService;
import com.switchfully.eurder.service.order.dto.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping(path = "/{itemId}",consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String addPurchase(@RequestHeader String userId, @RequestBody int amount, @PathVariable String itemId){
        return orderService.addPurchase(userId,itemId,amount);
    }
    @PatchMapping(path = "/checkout", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderDto checkout(@RequestHeader String userId){
        return orderService.checkout(userId);
    }
}
