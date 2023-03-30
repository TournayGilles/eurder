package com.switchfully.eurder.api;

import com.switchfully.eurder.service.order.OrderService;
import com.switchfully.eurder.service.order.dto.OrderDto;
import com.switchfully.eurder.service.order.dto.OrderReportDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String checkout(@RequestHeader String userId){
        return orderService.checkout(userId);
    }
    @PostMapping(path = "/reorder/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String orderBasedOnOldOrder(@RequestHeader String userId, @PathVariable String orderId){
        return orderService.orderBasedOnOldOrder(userId, orderId);
    }
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public OrderReportDto getOrdersForCustomer(@RequestHeader String userId){
        return orderService.getOrdersForCustomer(userId);
    }
}
