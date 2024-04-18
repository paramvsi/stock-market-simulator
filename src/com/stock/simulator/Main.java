package com.stock.simulator;

import com.stock.simulator.core.SimulatorEngine;
import com.stock.simulator.core.Stock;

import java.util.*;

public class Main {

    private static SimulatorEngine engine = new SimulatorEngine();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Stock> stocks = createStocks(50);  // Generate 50 stocks
        // Add stocks to the simulator
        stocks.forEach(engine::addStock);

        Thread simulationThread = new Thread(engine::start);
        simulationThread.start();

        while (true) {
            System.out.println("\nAvailable Commands: DISPLAY_ALL, DISPLAY_TOP_5, STOP, CREATE_USER, LOGIN, ADD_CASH, BUY_STOCK");
            System.out.print("Enter command: ");
            String input = scanner.nextLine();
            if ("STOP".equalsIgnoreCase(input.trim())) {
                engine.stop();
                break;
            }

            processCommand(input.trim().toUpperCase());
            // add more commands later processCommand(input.trim().toUpperCase());
        }

        try {
            simulationThread.join();  // Wait for the simulation thread to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted.");
        }
        scanner.close();
        System.out.println("Simulation stopped.");


    }

    private static void processCommand(String command) {

        switch (command) {
            case "DISPLAY_TOP_5": {
                engine.displayTopFiveStocks();
                break;
            }
            case "DISPLAY_ALL": {
                engine.displayAllStocks();
                break;
            }
            case "STOCK_HISTORY": {
                System.out.println("Enter the stock symbol for which you want historical prices:");
                String symbol = scanner.nextLine().trim();
                displayHistoricalPrices(symbol);
                break;
            }

            default:
                System.out.println("Enter a valid command");
        }
    }

    private static List<Stock> createStocks(int numberOfStocks) {
        List<Stock> stocks = new ArrayList<>();
        for (int i = 1; i <= numberOfStocks; i++) {
            stocks.add(new Stock("STOCK" + i, "Company " + i, 100.0 + Math.random() * 100));
        }
        return stocks;
    }

    private static void displayHistoricalPrices(String symbol) {
        Stock stock = engine.getStock(symbol);  // Method in SimulatorEngine to retrieve a Stock by symbol
        if (stock != null) {
            TreeMap<String, Double> historicalPrices = stock.getHistoricalPrices();
            System.out.println("Historical Prices for " + symbol + ":");
            for (Map.Entry<String, Double> entry : historicalPrices.entrySet()) {
                System.out.println("Date: " + entry.getKey() + ", Price: " + entry.getValue());
            }
        } else {
            System.out.println("Stock not found!");
        }
    }
}
