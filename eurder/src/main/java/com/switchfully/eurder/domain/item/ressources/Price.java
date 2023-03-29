package com.switchfully.eurder.domain.item.ressources;

import java.util.Objects;

public class Price {
    private final Double value;
    private final String currency;

    public Price(Double value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Double getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return value + " " + currency;
    }

    public Price calculatePrice(int amount) {
        return new Price(value * amount, currency);
    }
    public static Price addPrices(Price price1, Price price2){
        return new Price(price1.getValue() + price2.getValue(), price1.getCurrency());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(value, price.value) && Objects.equals(currency, price.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }
}
