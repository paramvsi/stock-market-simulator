package com.stock.simulator.strategies;

import com.stock.simulator.core.Stock;

import java.util.List;

public class SimpleMovingAverageStrategy implements TradingStrategy {
    private final int shortTermPeriod;
    private final int longTermPeriod;

    public SimpleMovingAverageStrategy(int shortTermPeriod, int longTermPeriod) {
        this.shortTermPeriod = shortTermPeriod;
        this.longTermPeriod = longTermPeriod;
    }

    @Override
    public void evaluate(Stock stock) {
        List<Double> prices = stock.getOrderedPriceList();

        if (prices.size() < longTermPeriod) {
            // Not enough data to evaluate
            return;
        }

        double shortTermAverage = calculateMovingAverage(prices, shortTermPeriod);
        double longTermAverage = calculateMovingAverage(prices, longTermPeriod);

        if (shortTermAverage > longTermAverage) {
            System.out.println("Should Buy Stock" + stock.getSymbol());
        } else if (shortTermAverage < longTermAverage) {
            System.out.println("Should Sell Stock " + stock.getSymbol());
        }
    }

    private double calculateMovingAverage(List<Double> prices, int period) {
        return prices.subList(prices.size() - period, prices.size()).stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);
    }
}

