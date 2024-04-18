package com.stock.simulator.core;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SimulatorEngine {
    private final Map<String, Stock> marketStocks;
    private boolean isRunning;

    public SimulatorEngine() {
        this.marketStocks = new HashMap<>();
        this.isRunning = false;
    }

    public void addStock(Stock stock) {
        marketStocks.put(stock.getSymbol(), stock);
    }


    public void start() {
        isRunning = true;
        while (isRunning) {
            simulateMarket();
            try {
                Thread.sleep(5000); // Pause for a second before the next market update
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Simulation interrupted");
            }
        }
    }

    public void stop() {
        isRunning = false;
    }

    private void simulateMarket() {
        for (Stock stock : marketStocks.values()) {
            double newPrice = simulatePriceChange(stock.getCurrentPrice());
            stock.setCurrentPrice(newPrice);
        }
    }

    private double simulatePriceChange(double currentPrice) {
        // Random price fluctuation logic here
        return currentPrice * (1 + (Math.random() - 0.5) * 0.1); // Fluctuate up to ±5%
    }

    public void displayTopFiveStocks() {
        System.out.println("Top 5 Stocks:");
        marketStocks.values().stream()
                .sorted(Comparator.comparingDouble(Stock::getCurrentPrice).reversed())
                .limit(5)
                .forEach(stock -> System.out.println(stock.getSymbol() + ": " + String.format("%.2f", stock.getCurrentPrice()) + ": " + String.format("%.2f%%", stock.getAllTimePriceChange())));
    }

    public void displayAllStocks() {
        System.out.println("Stock - Current Price - Price Change (%)");
        marketStocks.values()
                .forEach(stock -> {
                    System.out.printf("%s - $%.2f - %.2f%%\n", stock.getSymbol(), stock.getCurrentPrice(), stock.getAllTimePriceChange());
                });
    }

    public Stock getStock(String symbol) {
        return marketStocks.get(symbol);
    }
}
