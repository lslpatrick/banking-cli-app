package main;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<BankAccount> accounts;
    private int currentAccountIndex;

    public Bank() {
        this.accounts = new ArrayList<>();
        this.currentAccountIndex = 0;
        this.accounts.add(new BankAccount("Default"));
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

     public void changeCurrentAccount(int newAccountIndex) {
        if (newAccountIndex < 0 || newAccountIndex >= accounts.size()) {
            throw new IllegalArgumentException("Invalid account index.");
        }
        currentAccountIndex = newAccountIndex;
    }

    public void createAccount(String accountName) {
        accounts.add(new BankAccount(accountName));
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
        if (targetAccountIndex < 0 || targetAccountIndex >= accounts.size()) {
            throw new IllegalArgumentException();
        }

        if (targetAccountIndex == currentAccountIndex) {
            throw new IllegalArgumentException();
        }

        if (amount <= 0) {
            throw new IllegalArgumentException();
        }

        BankAccount targetAccount = accounts.get(targetAccountIndex);
        getCurrentAccount().withdraw(amount);
        targetAccount.deposit(amount);
    }
}
