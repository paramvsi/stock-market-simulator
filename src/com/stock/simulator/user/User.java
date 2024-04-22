package com.stock.simulator.user;

import com.stock.simulator.core.Portfolio;

public class User {
    private final String username;
    private final String password;  // Remember, store hashed passwords in production
    private final Portfolio portfolio;  // Each user has their own portfolio

    public User(String username, String password, double initialCash) {
        this.username = username;
        this.password = password;
        this.portfolio = new Portfolio(initialCash);  // Initialize with some starting cash
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);  // Hash and check in real applications
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }
}

