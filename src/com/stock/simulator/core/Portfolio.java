package com.stock.simulator.core;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private double cash;
    private final Map<Stock, Integer> holdings;
    private double totalInvested;

    public Portfolio(double initialCash) {
        this.holdings = new HashMap<>();
        this.cash = initialCash;
        this.totalInvested = 0;
    }

    public double getCash() {
        return cash;
    }

    public void addMoreCash(double cash) {
        this.cash += cash;
    }

    public Map<Stock, Integer> getHoldings() {
        return holdings;
    }

    public void buyStock(Stock stock, int quantity, double price) {
        if (price * quantity > cash) {
            throw new IllegalArgumentException("Not enough cash to buy " + quantity + " shares of " + stock.getName());
        }

        holdings.merge(stock, quantity, Integer::sum);  // Add the stock or increment the quantity
        cash -= price * quantity;  // Deduct the cost from cash
        totalInvested += price * quantity;
    }


    public void sellStock(Stock stock, int quantity, double price) {
        Integer currentQuantity = holdings.get(stock);
        if (currentQuantity == null || currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough shares to sell " + quantity + " of " + stock.getName());
        }

        holdings.put(stock, currentQuantity - quantity);  // Decrement or remove the stock
        if (holdings.get(stock) == 0) {
            holdings.remove(stock);
        }
        cash += price * quantity;  // Add the proceeds to cash
    }

    public double getTotalValue() {
        double totalValue = cash;
        for (Map.Entry<Stock, Integer> entry : holdings.entrySet()) {
            totalValue += entry.getKey().getCurrentPrice() * entry.getValue();
        }
        return totalValue;
    }

    public double calculateROI() {
        double currentValue = getTotalValue();
        if (totalInvested > 0) {
            return ((currentValue - totalInvested) / totalInvested) * 100;
        }
        return 0;  // Return 0 if no investment was made to avoid division by zero
    }
}
