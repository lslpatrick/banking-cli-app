package main;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<BankAccount> accounts;
    private int currentAccountIndex;

    public Bank() {
        this.accounts = new ArrayList<>();
        this.currentAccountIndex = 0;
        this.accounts.add(new BankAccount("Default", "Checking"));
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public int getCurrentAccountIndex() {
        return currentAccountIndex;
    }

    public void setCurrentAccountIndex(int index) {
        this.currentAccountIndex = index;
    }

    public BankAccount getCurrentAccount() {
        return accounts.get(currentAccountIndex);
    }

    public void checkIndexAvailability(int index) {
        if (index < 0 || index >= accounts.size()) {
            throw new IllegalArgumentException("Invalid account index.");
        }
    }

    public void changeCurrentAccount(int newAccountIndex) {
        checkIndexAvailability(newAccountIndex);
        currentAccountIndex = newAccountIndex;
    }

    public void createAccount(String accountName, String accountType) {
        accounts.add(new BankAccount(accountName, accountType));
    }

    public String closeCurrentAccount() {
        if (accounts.size() == 1) {
            throw new IllegalArgumentException("Unable to close the only remaining account.");
        }

        if (getCurrentAccount().getBalance() != 0) {
            throw new IllegalArgumentException("Unable to close an account with a remaining balance.");
        }

        String closedAccountName = getCurrentAccount().getAccountName();
        accounts.remove(currentAccountIndex);

        if (currentAccountIndex >= accounts.size()) {
            currentAccountIndex = 0;
        }

        return closedAccountName;
    }


    public void transferBetweenAccounts(int targetAccountIndex, double amount) {
        checkIndexAvailability(targetAccountIndex);

        if (targetAccountIndex == currentAccountIndex) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }

        BankAccount targetAccount = accounts.get(targetAccountIndex);
        getCurrentAccount().withdraw(amount);
        targetAccount.deposit(amount);
    }

    public void collectFeeFromAccount(int accountIndex, double feeAmount) {
        checkIndexAvailability(accountIndex);
        if (accounts.get(accountIndex).getBalance() < feeAmount) {
            throw new IllegalArgumentException("Insufficient balance to collect fee.");
        }
        accounts.get(accountIndex).collectFee(feeAmount);
    }

    /* 
    public void collectFeeFromAllAccounts(double feeAmount) {
        if (feeAmount <= 0) {
            throw new IllegalArgumentException("Fee amount must be greater than zero.");
        }

        for (BankAccount account : accounts) {
            account.collectFee(feeAmount);
        }
    }
    */

    public void addInterestToAccount(int accountIndex, double interestAmount) {
        checkIndexAvailability(accountIndex);
        accounts.get(accountIndex).addInterestPayment(interestAmount);
    }

    public void freezeAccount(int accountIndex) { //Admin
        checkIndexAvailability(accountIndex);
        accounts.get(accountIndex).freezeAccount();
    }

    public void unfreezeAccount(int accountIndex) { //Admin
        checkIndexAvailability(accountIndex);
        accounts.get(accountIndex).unfreezeAccount();
    }
}
