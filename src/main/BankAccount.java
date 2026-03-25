package main;

import java.util.List;

public class BankAccount {

    private double balance;
    private List<String> transactionHistory;
    private String accountName;



    public BankAccount() {
        this.accountName = "Default";
        this.balance = 0;
        this.transactionHistory = new java.util.ArrayList<>();
    }

    public BankAccount(String accountName) {
        this.accountName = accountName;
        this.balance = 0;
        this.transactionHistory = new java.util.ArrayList<>();
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
            this.transactionHistory.add("Deposited: $" + amount); //add desposit to transaction history 

        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double amount) {
        if(amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            this.transactionHistory.add("Withdrew: $" + amount); //add withdraw to transaction history
        } else {
            throw new IllegalArgumentException();
        }
    }   

    public double getBalance() {
        return this.balance;
    }

    public List<String> getTransactionHistory() {
        return this.transactionHistory;
    }

}
