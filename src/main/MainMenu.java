package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 10;
	private static final int MAX_SELECTION = 10086;

    private Scanner keyboardInput;
    private List<BankAccount> accounts;
    private int currentAccountIndex;


    public MainMenu() {
        this.keyboardInput = new Scanner(System.in);
        this.accounts = new ArrayList<>();
        this.currentAccountIndex = 0;
        this.accounts.add(new BankAccount("Default"));
    }

    public void displayOptions() {
        System.out.println();
        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("Current account: " + getCurrentAccount().getAccountName());
        System.out.println();
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Make a withdraw");
        System.out.println("3. Check balance");
        System.out.println("4. View transaction history");
        System.out.println("5. Create additional account");
        System.out.println("6. Change current account");
        System.out.println("10. Exit the app");

    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 1 || selection > max) {
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
        }
    }

    public BankAccount getCurrentAccount() { 
        return accounts.get(currentAccountIndex);
    }

    public void performDeposit() {
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        getCurrentAccount().deposit(depositAmount);
    }

    public void performWithdraw() {
        double withdrawAmount = -1;
        while(withdrawAmount < 0) {
            System.out.print("How much would you like to withdraw: ");
            withdrawAmount = keyboardInput.nextInt();
        }
        try {
            getCurrentAccount().withdraw(withdrawAmount);
            System.out.println("Withdrawal successful.");
            System.out.println("Current balance: " + getCurrentAccount().getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Withdrawal failed: insufficient funds or invalid amount.");
        }
    }

    public void viewTransactionHistory() {
        if (getCurrentAccount().getTransactionHistory().isEmpty()) {
            System.out.println("No transactions record found.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : getCurrentAccount().getTransactionHistory()) {
                System.out.println(transaction);
            }
        }
    }

    public void checkBalance() {
        System.out.println("Current balance: " + getCurrentAccount().getBalance());
    }

    public void createAccount() {
        System.out.print("Enter account name: ");
        String accountName = keyboardInput.next();

        accounts.add(new BankAccount(accountName));
        System.out.println("New account created.");
    }

    public void changeCurrentAccount() {
        System.out.println("Available accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).getAccountName());
        }

        System.out.print("Enter account number: ");
        int newAccountNumber = keyboardInput.nextInt();

        if (newAccountNumber >= 1 && newAccountNumber <= accounts.size()) {
            currentAccountIndex = newAccountNumber - 1;
            System.out.println("Switched to account: " + getCurrentAccount().getAccountName());
        } else {
            System.out.println("Invalid account number.");
        }
    }

    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
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
