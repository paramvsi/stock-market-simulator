package com.stock.simulator.data;

import com.stock.simulator.core.SimulatorEngine;
import com.stock.simulator.core.Stock;

import java.util.HashMap;
import java.util.Map;

public class DataGenerator {
    private Map<String, Stock> stocks = new HashMap<>();
    private SimulatorEngine engine;

    public DataGenerator(SimulatorEngine engine) {
        this.engine = engine;
    }

    public void initializeStocks() {
        String[] symbols = {"RELIANCE", "TCS", "HDFCBANK", "INFY", "HINDUNILVR", "BHARTIARTL", "ITC", "SBI", "HCLTECH", "ICICIBANK",
                "WIPRO", "LT", "ONGC", "MARUTI", "AXISBANK", "BAJFINANCE", "NTPC", "COALINDIA", "POWERGRID", "NESTLEIND",
                "SUNPHARMA", "ULTRACEMCO", "ASIANPAINT", "INDUSINDBK", "TATASTEEL", "BAJAJ-AUTO", "HEROMOTOCO", "M&M", "DIVISLAB", "CIPLA",
                "TECHM", "DRREDDY", "GRASIM", "TITAN", "ADANIPORTS", "BRITANNIA", "IOC", "BPCL", "HDFCLIFE", "JSWSTEEL",
                "SBILIFE", "HINDALCO", "EICHERMOT", "UPL", "BAJAJFINSV", "SHREECEM", "HDFC", "KOTAKBANK", "ONGC", "DABUR"};
        String[] names = {"Reliance Industries Limited", "Tata Consultancy Services", "HDFC Bank Ltd", "Infosys Ltd", "Hindustan Unilever Ltd", "Bharti Airtel Ltd", "ITC Ltd", "State Bank of India", "HCL Technologies Ltd", "ICICI Bank Ltd",
                "Wipro Ltd", "Larsen & Toubro Ltd", "Oil and Natural Gas Corporation", "Maruti Suzuki India Ltd", "Axis Bank Ltd", "Bajaj Finance Ltd", "NTPC Ltd", "Coal India Ltd", "Power Grid Corporation of India Ltd", "Nestle India Ltd",
                "Sun Pharmaceutical Industries Ltd", "UltraTech Cement Ltd", "Asian Paints Ltd", "IndusInd Bank Ltd", "Tata Steel Ltd", "Bajaj Auto Ltd", "Hero MotoCorp Ltd", "Mahindra & Mahindra Ltd", "Divi's Laboratories Ltd", "Cipla Ltd",
                "Tech Mahindra Ltd", "Dr. Reddy's Laboratories Ltd", "Grasim Industries Ltd", "Titan Company Ltd", "Adani Ports and Special Economic Zone Ltd", "Britannia Industries Ltd", "Indian Oil Corporation Ltd", "Bharat Petroleum Corporation Ltd", "HDFC Life Insurance Company Ltd", "JSW Steel Ltd",
                "SBI Life Insurance Company Ltd", "Hindalco Industries Ltd", "Eicher Motors Ltd", "UPL Ltd", "Bajaj Finserv Ltd", "Shree Cement Ltd", "Housing Development Finance Corporation Ltd", "Kotak Mahindra Bank Ltd", "Oil and Natural Gas Corporation", "Dabur India Ltd"};

        for (int i = 0; i < symbols.length; i++) {
            double startingPrice = 1000 + Math.random() * 500; // Generate a random price between 1000 and 1500
            Stock stock = new Stock(symbols[i], names[i], startingPrice);
            stocks.put(symbols[i], stock);
            engine.addStock(stock);
        }
    }
}
