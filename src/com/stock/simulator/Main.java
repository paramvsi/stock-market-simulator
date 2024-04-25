package com.stock.simulator;

import com.stock.simulator.core.SimulatorEngine;
import com.stock.simulator.core.Stock;
import com.stock.simulator.data.DataGenerator;
import com.stock.simulator.user.User;
import com.stock.simulator.user.UserManager;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    private static final SimulatorEngine engine = new SimulatorEngine();
    private static final UserManager userManager = new UserManager();
    private static final Scanner scanner = new Scanner(System.in);

    private static User currentUser = null;

    private static boolean exit = false;

    public static void main(String[] args) {

        DataGenerator dataGenerator = new DataGenerator(engine);
        dataGenerator.initializeStocks();

        Thread simulationThread = new Thread(engine::start);
        simulationThread.start();


        while (!exit) {
            displayOptions(currentUser != null);

            System.out.print("Enter command: ");
            int command = scanner.nextInt();
            processCommand(command);

        }
        scanner.close();

    }

    private static void processCommand(int command) {

        switch (command) {
            case 0:
                displayOptions(currentUser != null);
                break;
            case 1:
                if (currentUser == null) registerUser();
                else System.out.println("Please logout first.");
                break;
            case 2:
                if (currentUser == null) loginUser();
                else System.out.println("Already logged in as " + currentUser.getUsername());
                break;
            case 3:
                if (currentUser != null) addCash();
                else System.out.println("Please login first.");
                break;
            case 4:
                engine.displayAllStocks();
                break;
            case 5:
                engine.displayTopFiveStocks();
                break;
            case 6:
                System.out.println("Enter the stock symbol for which you want historical prices:");
                String symbol = scanner.nextLine().trim();
                displayHistoricalPrices(symbol);
                break;
            case 7:
                if (currentUser != null) showPortfolio();
                else System.out.println("Please login first.");
                break;

            case 8:
                if (currentUser != null) buyStock();
                else System.out.println("Please login first.");
                break;
            case 9:
                if (currentUser != null) sellStock();
                else System.out.println("Please login first.");
                break;
            case 11:
                if (currentUser != null) {
                    System.out.println("Logging out...");
                    currentUser = null;
                } else {
                    System.out.println("No user currently logged in.");
                }
                break;
            case 10:
                engine.stop();
                exit = true;
                System.out.println("Exiting system.");
                break;
            default:
                System.out.println("Invalid command. Please try again.");
                break;
        }
    }


    private static void displayOptions(boolean loggedIn) {
        if (!loggedIn) {
            notLoggedInOptions();
        } else {
            loggedInOptions();
        }
    }

    private static void notLoggedInOptions() {
        System.out.println("Available Options:");
        System.out.println("1 - Register a new user");
        System.out.println("2 - Login with an existing user");
        System.out.println("4 - Display All Stocks");
        System.out.println("5 - Display Top 5 Stocks");
        System.out.println("6 - Display Stock History");
        System.out.println("10 - Exit the application");
    }

    private static void loggedInOptions() {
        System.out.println("Available Options:");
        System.out.println("3 - Add cash to your portfolio");
        System.out.println("4 - Display All Stocks");
        System.out.println("5 - Display Top 5 Stocks");
        System.out.println("6 - Display Stock History");
        System.out.println("7 - Show Portfolio");
        System.out.println("8 - Buy Stock");
        System.out.println("9 - Sell Stock");
        System.out.println("11 - Logout");
        System.out.println("10 - Exit the application");
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        boolean success = userManager.registerUser(username, password);
        if (success) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Username already taken. Please choose a different username.");
        }
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        User user = userManager.loginUser(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Check your username and password.");
        }
    }

    private static void addCash() {
        if (currentUser != null) {
            System.out.print("Enter amount of cash to add: ");
            double amount = scanner.nextDouble();
            currentUser.getPortfolio().addMoreCash(amount);
            System.out.println(amount + " added to " + currentUser.getUsername() + "'s portfolio.");
        }
    }

    private static void showPortfolio() {
        if (currentUser != null) {
            double cash = currentUser.getPortfolio().getCash();
            Map<Stock, Integer> holdings = currentUser.getPortfolio().getHoldings();

            System.out.println("Cash available :: " + cash);

            if (holdings.isEmpty()) {
                System.out.println("No stocks in portfolio.");
            } else {
                System.out.println("Your holdings:");
                for (Map.Entry<Stock, Integer> entry : holdings.entrySet()) {
                    Stock stock = entry.getKey();
                    Integer quantity = entry.getValue();
                    System.out.println(stock.getSymbol() + " -- " + quantity);
                }
            }

            System.out.println("################################################");

        }
    }

    private static void buyStock() {
        if (currentUser != null) {
            System.out.print("Enter stock to buy: ");
            String symbol = scanner.next();
            Stock stock = engine.getStock(symbol);
            currentUser.getPortfolio().buyStock(stock, 1, stock.getCurrentPrice());
            System.out.println("Stock purchased successfully");
        }
    }

    private static void sellStock() {
        if (currentUser != null) {
            System.out.print("Enter stock to sell: ");
            String symbol = scanner.next();
            System.out.print("Enter qty to sell: ");
            int qty = scanner.nextInt();
            Stock stock = engine.getStock(symbol);
            currentUser.getPortfolio().sellStock(stock, qty, stock.getCurrentPrice());
            System.out.println("Stock sold successfully");
        }
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
