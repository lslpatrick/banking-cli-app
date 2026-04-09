package main;

import java.util.List;
import java.util.ArrayList;

public class BankAccount {
    private String accountName;
    private String accountType;
    private double balance;
    private List<String> transactionHistory;
    private boolean frozen;
    
    public BankAccount() {
        this.accountName = "Default";
        this.accountType = "Checking";
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
        this.frozen = false;
    }

    public BankAccount(String accountName, String accountType) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
        this.frozen = false;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public double getBalance() {
        return this.balance;
    }

    public List<String> getTransactionHistory() {
        return this.transactionHistory;
    }

    public void deposit(double amount) {
        if (this.frozen) {
            throw new IllegalStateException("Account is frozen.");
        }

        if (amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposited: $" + amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double amount) {
        if (this.frozen) {
            throw new IllegalStateException("Account is frozen.");
        }

        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            this.transactionHistory.add("Withdrew: $" + amount);
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

    public boolean isFrozen() {
        return this.frozen;
    }

    public void freezeAccount() {
        this.frozen = true;
        this.transactionHistory.add("Account frozen.");
    }

    public void unfreezeAccount() {
        this.frozen = false;
        this.transactionHistory.add("Account unfrozen.");
    }
}