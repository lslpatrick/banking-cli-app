package main;

import java.util.Scanner;

public class MainMenu {
    private static final int EXIT_SELECTION = 11;

    private final Scanner keyboardInput;
    private final InputHelper inputHelper;
    private final Bank bank;
    private final CustomerMenu customerMenu;
    private final AdminMenu adminMenu;

    public MainMenu() {
        this.keyboardInput = new Scanner(System.in);
        this.inputHelper = new InputHelper(keyboardInput);
        this.bank = new Bank();
        this.customerMenu = new CustomerMenu(inputHelper, bank);
        this.adminMenu = new AdminMenu(inputHelper, bank);
    }

    public void displayOptions() {
        System.out.println();
        System.out.println("==== Welcome to the 237 Bank App ====");
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
        System.out.println("10. Generate bank statement");
        System.out.println("11. Exit the app");
    }

    public int getUserSelection(int max) {
        return inputHelper.readIntInRange("Please make a selection: ", 1, max);
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
                customerMenu.generateBankStatement();
                break;
            case 11:
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
    }

    public void authenticateCustomer() {
        if (!bank.hasCustomerPin()) {
            System.out.println("=== Customer PIN Setup ===");
            System.out.println("Please create a 4-digit PIN before using account services.");

            while (true) {
                String pin = inputHelper.readLine("Enter new PIN: ");

                String confirmPin = inputHelper.readLine("Confirm new PIN: ");

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
    }

    public void run() {
        authenticateCustomer();

        int selection = -1;
        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(EXIT_SELECTION);
            processInput(selection);
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }
}
