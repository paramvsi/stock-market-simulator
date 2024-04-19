package com.stock.simulator.strategies;
import com.stock.simulator.core.Stock;

public interface TradingStrategy {
    /**
     * Evaluate the given stock and decide whether to buy, hold, or sell.
     *
     * @param stock the stock to evaluate
     */
    void evaluate(Stock stock);
}

