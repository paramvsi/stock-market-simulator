package com.stock.simulator.core;

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
                Thread.sleep(1000); // Pause for a second before the next market update
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
}
