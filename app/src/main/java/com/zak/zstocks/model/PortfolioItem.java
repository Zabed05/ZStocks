package com.zak.zstocks.model;

public class PortfolioItem {

    private String stockName;
    private int quantity;

    public PortfolioItem() {} // REQUIRED

    public PortfolioItem(String stockName, int quantity) {
        this.stockName = stockName;
        this.quantity = quantity;
    }

    public String getStockName() { return stockName; }
    public int getQuantity() { return quantity; }
}
