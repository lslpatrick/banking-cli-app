package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<BankAccount> accounts;
    private int currentAccountIndex;
    private String customerPin;

    public Bank() {
        this.accounts = new ArrayList<>();
        this.currentAccountIndex = 0;
        this.customerPin = null;
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

    public boolean hasCustomerPin() {
        return customerPin != null;
    }

    public boolean isValidPinFormat(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    public void setCustomerPin(String pin) {
        if (!isValidPinFormat(pin)) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits.");
        }
        this.customerPin = pin;
    }

    public boolean verifyCustomerPin(String pin) {
        return customerPin != null && customerPin.equals(pin);
    }

    public boolean changeCustomerPin(String currentPin, String newPin) {
        if (!verifyCustomerPin(currentPin)) {
            return false;
        }

        if (!isValidPinFormat(newPin)) {
            return false;
        }

        this.customerPin = newPin;
        return true;
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

    public void addInterestToAccount(int accountIndex, double interestAmount) {
        checkIndexAvailability(accountIndex);
        accounts.get(accountIndex).addInterestPayment(interestAmount);
    }

    public void addInterestToSavingAccount(int accountIndex) {
        checkIndexAvailability(accountIndex);
        if (!accounts.get(accountIndex).getAccountType().equals("Saving")) {
            throw new IllegalArgumentException("Only saving accounts can use this interest option.");
        }

        double interestAmount = accounts.get(accountIndex).getBalance() * 0.02;
        accounts.get(accountIndex).addInterestPayment(interestAmount);
    }

    public void generateBankStatement(int accountIndex, String fileName) throws IOException {
        checkIndexAvailability(accountIndex);

        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("Statement file name cannot be empty.");
        }

        BankAccount account = accounts.get(accountIndex);

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("237 Bank Statement");
            writer.println("==================");
            writer.println("Account Name: " + account.getAccountName());
            writer.println("Account Type: " + account.getAccountType());
            writer.println("Current Balance: $" + account.getBalance());
            writer.println();
            writer.println("Transaction History:");

            if (account.getTransactionHistory().isEmpty()) {
                writer.println("No transactions history found.");
            } else {
                for (String transaction : account.getTransactionHistory()) {
                    writer.println(transaction);
                }
            }
        }
    }

    public void freezeAccount(int accountIndex) {
        checkIndexAvailability(accountIndex);
        accounts.get(accountIndex).freezeAccount();
    }

    public void unfreezeAccount(int accountIndex) {
        checkIndexAvailability(accountIndex);
        accounts.get(accountIndex).unfreezeAccount();
    }
}
