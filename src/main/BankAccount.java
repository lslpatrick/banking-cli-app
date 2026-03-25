package main;

import java.util.List;
import java.util.ArrayList;

public class BankAccount {
    private String accountName;
    private double balance;
    private List<String> transactionHistory;
    
    public BankAccount() {
        this.accountName = "Default";
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public BankAccount(String accountName) {
        this.accountName = accountName;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountName() {
        return this.accountName;
    }

    public double getBalance() {
        return this.balance;
    }

    public List<String> getTransactionHistory() {
        return this.transactionHistory;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposited: $" + amount); // Adds deposit to transaction history
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            this.transactionHistory.add("Withdrew: $" + amount); // Adds withdraw to transaction history
        } else {
            throw new IllegalArgumentException();
        }
    }   
}
