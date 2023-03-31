package com.switchfully.eurder.domain.item.ressources;

import java.util.Objects;

public record Price(Double value, String currency) {

    @Override
    public String toString() {
        return value + " " + currency;
    }

    public Price calculatePrice(int amount) {
        return new Price(value * amount, currency);
    }

    public static Price addPrices(Price price1, Price price2) {
        return new Price(price1.value() + price2.value(), price1.currency());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(value, price.value) && Objects.equals(currency, price.currency);
    }

}
