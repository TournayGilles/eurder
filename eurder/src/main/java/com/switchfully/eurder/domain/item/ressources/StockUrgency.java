package com.switchfully.eurder.domain.item.ressources;

public enum StockUrgency {
    STOCK_LOW,
    STOCK_MEDIUM,
    STOCK_HIGH;

    public static StockUrgency setStockUrgency(int stock){
        if (stock < 5){
            return STOCK_LOW;
        }
        if (stock < 10){
            return STOCK_MEDIUM;
        }
        return STOCK_HIGH;
    }
}
