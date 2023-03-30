package com.switchfully.eurder.service.order.dto;

import java.util.List;

public class OrderReportDto {
    public final List<OrderDto> orders;
    public final String totalPrice;

    public OrderReportDto(List<OrderDto> orders, String totalPrice) {
        this.orders = orders;
        this.totalPrice = totalPrice;
    }
}
