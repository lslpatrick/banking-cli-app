package main;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
    private Scanner keyboardInput;
    private Bank bank;

    public CustomerMenu(Scanner keyboardInput, Bank bank) {
        this.keyboardInput = keyboardInput;
        this.bank = bank;
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while (selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }

    public void performDeposit() {
        double depositAmount = -1;
        while (depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextDouble();
        }

        try {
            bank.getCurrentAccount().deposit(depositAmount);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void performWithdraw() {
        double withdrawAmount = -1;
        while (withdrawAmount < 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextDouble();
        }

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
        System.out.println("Current balance: " + bank.getCurrentAccount().getBalance());
    }

    public void createAccount() {
        System.out.println("Select account type:");
        System.out.println("1. Saving");
        System.out.println("2. Checking");

        int typeSelection = getUserSelection(2);

        String accountType;
        if (typeSelection == 1) {
            accountType = "Saving";
        } else {
            accountType = "Checking";
        }

        System.out.print("Enter account name: ");
        String accountName = keyboardInput.next();

        bank.createAccount(accountName, accountType);
        System.out.println("New " + accountType + " account created.");
    }

    public void changeCurrentAccount() {
        List<BankAccount> accounts = bank.getAccounts();
        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).getAccountName() + " (" + accounts.get(i).getAccountType() + ")");
        }

        System.out.print("Enter account number: ");
        int newAccountNumber = keyboardInput.nextInt();

        try {
            bank.changeCurrentAccount(newAccountNumber - 1);
            System.out.println("Switched to account: " + bank.getCurrentAccount().getAccountName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeCurrentAccount() {
        try {
            String closedAccountName = bank.closeCurrentAccount();
            System.out.println("Account closed: " + closedAccountName);
            System.out.println("Current account: " + bank.getCurrentAccount().getAccountName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transferMoney() {
        if (bank.getAccounts().size() == 1) {
            System.out.println("You need at least two accounts to make a transfer.");
            return;
        }

        double transferAmount = -1;
        while (transferAmount <= 0) {
            System.out.print("How much would you like to transfer: ");
            transferAmount = keyboardInput.nextDouble();
        }

        System.out.println("Available accounts:");
        for (int i = 0; i < bank.getAccounts().size(); i++) {
            System.out.println((i + 1) + ". " + bank.getAccounts().get(i).getAccountName());
        }

        System.out.print("Enter target account number: ");
        int targetAccountNumber = keyboardInput.nextInt();

        try {
            bank.transferBetweenAccounts(targetAccountNumber - 1, transferAmount);
            System.out.println("Transfer successful.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateBankStatement() {
        System.out.print("Enter statement file name: ");
        String fileName = keyboardInput.next();

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
