package main;

import java.util.Scanner;

public class MainMenu {
    private static final int EXIT_SELECTION = 10;
    private static final int MAX_SELECTION = 10086;

    private Scanner keyboardInput;
    private Bank bank;
    private CustomerMenu customerMenu;
    private AdminMenu adminMenu;

    public MainMenu() {
        this.keyboardInput = new Scanner(System.in);
        this.bank = new Bank();
        this.customerMenu = new CustomerMenu(keyboardInput, bank);
        this.adminMenu = new AdminMenu(keyboardInput, bank);
    }

    public void displayOptions() {
        System.out.println();
        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("Current account: " + bank.getCurrentAccount().getAccountName() + " (" + bank.getCurrentAccount().getAccountType() + ")");
        System.out.println("Admin mode: " + (adminMenu.isAdminMode() ? "ON" : "OFF"));
        System.out.println();
        System.out.println("1. Make a deposit");
        System.out.println("2. Make a withdraw");
        System.out.println("3. Check balance");
        System.out.println("4. View transaction history");
        System.out.println("5. Create new account");
        System.out.println("6. Switch account");
        System.out.println("7. Close current account");
        System.out.println("8. Transfer money to another account");
        System.out.println("9. Enter administrator mode");
        System.out.println("10. Exit the app");
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
                customerMenu.performDeposit();
                break;
            case 2:
                customerMenu.performWithdraw();
                break;
            case 3:
                customerMenu.checkBalance();
                break;
            case 4:
                customerMenu.viewTransactionHistory();
                break;
            case 5:
                customerMenu.createAccount();
                break;
            case 6:
                customerMenu.changeCurrentAccount();
                break;
            case 7:
                customerMenu.closeCurrentAccount();
                break;
            case 8:
                customerMenu.transferMoney();
                break;
            case 9:
                adminMenu.toggleAdminMode();
                break;
            case 10:
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
    }

    public void authenticateCustomer() {
        System.out.println("=== Customer Login ===");

        if (!bank.hasCustomerPin()) {
            System.out.println("No customer PIN has been set.");
            System.out.println("Please create a 4-digit PIN before using account services.");

            while (true) {
                System.out.print("Enter new PIN: ");
                String pin = keyboardInput.nextLine();

                System.out.print("Confirm new PIN: ");
                String confirmPin = keyboardInput.nextLine();

                if (!pin.equals(confirmPin)) {
                    System.out.println("PINs do not match. Please try again.");
                    continue;
                }

                try {
                    bank.setCustomerPin(pin);
                    System.out.println("PIN created successfully.");
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            return;
        }

        int attemptsRemaining = 3;

        while (attemptsRemaining > 0) {
            System.out.print("Enter customer PIN: ");
            String pin = keyboardInput.nextLine();

            if (bank.verifyCustomerPin(pin)) {
                System.out.println("Login successful.");
                return;
            }

            attemptsRemaining--;
            System.out.println("Incorrect PIN. Attempts remaining: " + attemptsRemaining);
        }

        System.out.println("Too many incorrect attempts. Exiting app.");
        System.exit(0);
    }

    public void run() {
        authenticateCustomer();

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