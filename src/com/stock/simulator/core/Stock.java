package com.stock.simulator.core;

import java.util.Optional;
import java.util.TreeMap;

public class Stock {
    private String name;
    private String symbol;
    private double currentPrice;
    private final TreeMap<String, Double> historicalPrices; // since tree map maintains order

    // Constructor to initialize the stock with a symbol, name, and starting price
    public Stock(String symbol, String name, double startingPrice) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = startingPrice;
        this.historicalPrices = new TreeMap<>();
        this.historicalPrices.put(java.time.LocalDateTime.now().toString(), startingPrice);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;

        this.historicalPrices.put(java.time.LocalDateTime.now().toString(), currentPrice);
    }

    public TreeMap<String, Double> getHistoricalPrices() {
        return historicalPrices;
    }

    public double getAllTimePriceChange() {
        if (historicalPrices.isEmpty()) {
            return 0.0; // No historical data to compare against
        }

        // Get the first entry in the TreeMap (earliest date)
        Optional<Double> firstPrice = getHistoricalPrices().values().stream().findFirst();

        double firstRecordedPrice = firstPrice.get();
        // Calculate percentage change
        return ((currentPrice - firstRecordedPrice) / firstRecordedPrice) * 100;
    }

    @Override
    public String toString() {
        return symbol + " - " + currentPrice + " - " + getAllTimePriceChange();
    }
}
