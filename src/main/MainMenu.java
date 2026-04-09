package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private static final int EXIT_SELECTION = 12;
    private static final int MAX_SELECTION = 10086;

    private Scanner keyboardInput;
    private Bank bank;
    private boolean adminMode;

    public MainMenu() {
        this.keyboardInput = new Scanner(System.in);
        this.bank = new Bank();
        this.adminMode = false;
    }

    public void displayOptions() {
        System.out.println();
        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("Current account: " + bank.getCurrentAccount().getAccountName());
        System.out.println("Admin mode: " + (adminMode ? "ON" : "OFF"));
        System.out.println();
        System.out.println("1. Make a deposit");
        System.out.println("2. Make a withdraw");
        System.out.println("3. Check balance");
        System.out.println("4. View transaction history");
        System.out.println("5. Create additional account");
        System.out.println("6. Change current account");
        System.out.println("7. Close current account");
        System.out.println("8. Transfer money to another account");
        System.out.println("9. Enter/Exit admin mode");
        if (adminMode) {
            System.out.println("10. Collect fee from an account");
            System.out.println("11. Add interest payment to an account");
        }

        System.out.println("12. Exit the app");
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while (selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1:
                performDeposit();
                break;
            case 2:
                performWithdraw();
                break;
            case 3:
                checkBalance();
                break;
            case 4:
                viewTransactionHistory();
                break;
            case 5:
                createAccount();
                break;
            case 6:
                changeCurrentAccount();
                break;
            case 7:
                closeCurrentAccount();
                break;
            case 8:
                transferMoney();
                break;
            case 9:
                toggleAdminMode();
                break;
            case 10:
                if (adminMode) {
                    collectFeeAdmin();
                } else {
                    System.out.println("Invalid selection.");
                }
                break;
            case 11:
                if (adminMode) {
                    addInterestAdmin();
                } else {
                    System.out.println("Invalid selection.");
                }
                break;
            case 12:
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while (depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        bank.getCurrentAccount().deposit(depositAmount);
    }

    public void performWithdraw() {
        double withdrawAmount = -1;
        while (withdrawAmount < 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextInt();
        }
        try {
            bank.getCurrentAccount().withdraw(withdrawAmount);
            System.out.println("Withdrawal successful.");
            System.out.println("Current balance: " + bank.getCurrentAccount().getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Withdrawal failed: insufficient funds or invalid amount.");
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
        System.out.print("Enter account name: ");
        String accountName = keyboardInput.next();
        bank.createAccount(accountName);
        System.out.println("New account created.");
    }

    public void changeCurrentAccount() {
        List<BankAccount> accounts = bank.getAccounts();
        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).getAccountName());
        }

        System.out.print("Enter account number: ");
        int newAccountNumber = keyboardInput.nextInt();

        try {
            bank.changeCurrentAccount(newAccountNumber - 1);
            System.out.println("Switched to account: " + bank.getCurrentAccount().getAccountName());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid account number.");
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

        bank.transferBetweenAccounts(targetAccountNumber - 1, transferAmount);
        System.out.println("Transfer successful.");
    }

    public void toggleAdminMode() {
        adminMode = !adminMode;
        System.out.println("Admin mode is now " + (adminMode ? "ON" : "OFF"));
    }

    public void collectFeeAdmin() {
        List<BankAccount> accounts = bank.getAccounts();

        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).getAccountName());
        }

        System.out.print("Enter account number: ");
        int accountNumber = keyboardInput.nextInt();

        System.out.print("Enter fee amount: ");
        double feeAmount = keyboardInput.nextDouble();

        bank.collectFeeFromAccount(accountNumber - 1, feeAmount);
        System.out.println("Fee collected successfully.");
    }

    public void addInterestAdmin() {
        List<BankAccount> accounts = bank.getAccounts();

        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).getAccountName());
        }

        System.out.print("Enter account number: ");
        int accountNumber = keyboardInput.nextInt();

        System.out.print("Enter interest amount: ");
        double interestAmount = keyboardInput.nextDouble();

        bank.addInterestToAccount(accountNumber - 1, interestAmount);
        System.out.println("Interest payment added successfully.");
    }


    public void run() {
        int selection = -1;
        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }
}
