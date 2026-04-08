package main;

import java.util.List;
import java.util.ArrayList;

public class BankAccount {
    private String accountName;
    private double balance;
    private List<String> transactionHistory;
    
    // Constructors
    public BankAccount() {
        this.accountName = "Default";
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    // Constructor with account name
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

    public void collectFee(double feeAmount) {
        if (feeAmount <= 0 || feeAmount > this.balance) {
            throw new IllegalArgumentException();
        }

        this.balance -= feeAmount;
        this.transactionHistory.add("Collected fee: $" + feeAmount);
    }

    public void addInterestPayment(double interestAmount) {
        if (interestAmount <= 0) {
            throw new IllegalArgumentException();
        }

        this.balance += interestAmount;
        this.transactionHistory.add("Interest payment: $" + interestAmount);
    }
}
