package com.switchfully.eurder.service.order.dto;

import java.util.List;
import java.util.Objects;

public class OrderReportDto {
    public final List<OrderDto> orders;
    public final String totalPrice;

    public OrderReportDto(List<OrderDto> orders, String totalPrice) {
        this.orders = orders;
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderReportDto that = (OrderReportDto) o;
        return Objects.equals(orders, that.orders) && Objects.equals(totalPrice, that.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders, totalPrice);
    }
}
