package main;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
    private final InputHelper inputHelper;
    private final Bank bank;

    public CustomerMenu(InputHelper inputHelper, Bank bank) {
        this.inputHelper = inputHelper;
        this.bank = bank;
    }

    public CustomerMenu(Scanner keyboardInput, Bank bank) {
        this(new InputHelper(keyboardInput), bank);
    }

    public int getUserSelection(int max) {
        return inputHelper.readIntInRange("Please make a selection: ", 1, max);
    }

    private boolean verifyCustomerPin() {
        int attemptsRemaining = 3;

        while (attemptsRemaining > 0) {
            String pin = inputHelper.readLine("Enter customer PIN: ");

            if (bank.verifyCustomerPin(pin)) {
                return true;
            }

            attemptsRemaining--;
            System.out.println("Incorrect PIN. Attempts remaining: " + attemptsRemaining);
        }

        System.out.println("Too many incorrect attempts. Access denied.");
        return false;
    }

    public void performDeposit() {
        if (!verifyCustomerPin()) {
            return;
        }

        double depositAmount = inputHelper.readPositiveDouble("How much would you like to deposit: ");

        try {
            bank.getCurrentAccount().deposit(depositAmount);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void performWithdraw() {
        if (!verifyCustomerPin()) {
            return;
        }

        double withdrawAmount = inputHelper.readPositiveDouble("How much would you like to withdraw: ");

        try {
            boolean usedOverdrawProtection = bank.getCurrentAccount().withdrawWithOverdrawProtection(withdrawAmount);
            if (usedOverdrawProtection) {
                System.out.println("You are using overdraw.");
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewTransactionHistory() {
        if (!verifyCustomerPin()) {
            return;
        }

        if (bank.getCurrentAccount().getTransactionHistory().isEmpty()) {
            System.out.println("No transactions history found.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : bank.getCurrentAccount().getTransactionHistory()) {
                System.out.println(transaction);
            }
        }
    }

    public void checkBalance() {
        if (!verifyCustomerPin()) {
            return;
        }

        System.out.println("Current balance: " + bank.getCurrentAccount().getBalance());
    }

    public void createAccount() {
        if (!verifyCustomerPin()) {
            return;
        }

        System.out.println("Select account type:");
        System.out.println("1. Saving");
        System.out.println("2. Checking");

        int typeSelection = getUserSelection(2);

        AccountType accountType;
        if (typeSelection == 1) {
            accountType = AccountType.SAVING;
        } else {
            accountType = AccountType.CHECKING;
        }

        String accountName = inputHelper.readRequiredLine(
                "Enter account name: ",
                "Account name cannot be empty.");

        bank.createAccount(accountName, accountType);
        System.out.println("New " + accountType + " account created.");
    }

    public void changeCurrentAccount() {
        if (!verifyCustomerPin()) {
            return;
        }

        List<BankAccount> accounts = bank.getAccounts();
        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).getAccountName() + " (" + accounts.get(i).getAccountType() + ")");
        }

        int newAccountNumber = inputHelper.readIntInRange("Enter account number: ", 1, accounts.size());

        try {
            bank.changeCurrentAccount(newAccountNumber - 1);
            System.out.println("Switched to account: " + bank.getCurrentAccount().getAccountName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeCurrentAccount() {
        if (!verifyCustomerPin()) {
            return;
        }

        try {
            String closedAccountName = bank.closeCurrentAccount();
            System.out.println("Account closed: " + closedAccountName);
            System.out.println("Current account: " + bank.getCurrentAccount().getAccountName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transferMoney() {
        if (!verifyCustomerPin()) {
            return;
        }

        if (bank.getAccounts().size() == 1) {
            System.out.println("You need at least two accounts to make a transfer.");
            return;
        }

        double transferAmount = inputHelper.readPositiveDouble("How much would you like to transfer: ");

        System.out.println("Available accounts:");
        for (int i = 0; i < bank.getAccounts().size(); i++) {
            System.out.println((i + 1) + ". " + bank.getAccounts().get(i).getAccountName());
        }

        int targetAccountNumber = inputHelper.readIntInRange(
                "Enter target account number: ",
                1,
                bank.getAccounts().size());

        try {
            bank.transferBetweenAccounts(targetAccountNumber - 1, transferAmount);
            System.out.println("Transfer successful.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateBankStatement() {
        if (!verifyCustomerPin()) {
            return;
        }

        String fileName = inputHelper.readRequiredLine(
                "Enter statement file name: ",
                "Statement file name cannot be empty.");

        if (!fileName.endsWith(".txt")) {
            fileName += ".txt";
        }

        try {
            bank.generateBankStatement(bank.getCurrentAccountIndex(), fileName);
            System.out.println("Bank statement generated: " + fileName);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Unable to generate bank statement.");
        }
    }
}
