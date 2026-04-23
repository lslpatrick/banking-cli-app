package main;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private String ADMIN_PASSWORD;

    private Scanner keyboardInput;
    private Bank bank;
    private boolean adminMode;

    public AdminMenu(Scanner keyboardInput, Bank bank) {
        this.keyboardInput = keyboardInput;
        this.bank = bank;
        this.adminMode = false;
        this.ADMIN_PASSWORD = "0422";
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while (selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }

    public boolean isAdminMode() {
        return adminMode;
    }

    public boolean isCorrectAdminPassword(String password) {
        return ADMIN_PASSWORD.equals(password);
    }

    public boolean updateAdminPassword(String currentPassword, String newPassword) {
        if (!isCorrectAdminPassword(currentPassword)) {
            return false;
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }

        this.ADMIN_PASSWORD = newPassword;
        return true;
    }

    public void changeAdminPassword() {
        keyboardInput.nextLine();

        System.out.print("Enter current administrator password: ");
        String currentPassword = keyboardInput.nextLine();

        System.out.print("Enter new administrator password: ");
        String newPassword = keyboardInput.nextLine();

        if (updateAdminPassword(currentPassword, newPassword)) {
            System.out.println("Administrator password changed successfully.");
        } else {
            System.out.println("Password change failed.");
        }
    }

    public void toggleAdminMode() {
        if (adminMode) {
            adminMode = false;
            System.out.println("Admin mode is now OFF");
            return;
        }

        System.out.println("Default admin password is 0422. Please change it in the code for better security.");
        System.out.print("Enter administrator password: ");
        keyboardInput.nextLine();
        String password = keyboardInput.nextLine();

        if (isCorrectAdminPassword(password)) {
            adminMode = true;
            System.out.println("Admin mode is now ON");
            adminMenu();
        } else {
            System.out.println("Incorrect password. Access denied.");
        }
    }

    public void adminMenu() {
        while (adminMode) {
            System.out.println();
            System.out.println("=== Admin Menu ===");
            System.out.println("1. View All Accounts");
            System.out.println("2. Change Admin Password");
            System.out.println("3. Update Savings Interest Rate");
            System.out.println("4. Exit Admin Mode");

            int selection = getUserSelection(4);

            switch (selection) {
                case 1:
                    viewAllAccountsAdmin();
                    break;
                case 2:
                    changeAdminPassword();
                    break;
                case 3:
                    updateSavingsInterestRate();
                    break;
                case 4:
                    adminMode = false;
                    System.out.println("Admin mode is now OFF");
                    break;
                default:
                    System.out.println("Invalid selection.");
                    break;
            }
        }
    }

    public void viewAllAccountsAdmin() {
        while (true) {
            List<BankAccount> accounts = bank.getAccounts();

            System.out.println();
            System.out.println("=== Account List ===");
            System.out.println("Select an account to freeze, unfreeze, collect fee, or add interest.");

            // print all accounts with their status
            for (int i = 0; i < accounts.size(); i++) {
                BankAccount account = accounts.get(i);
                String status = account.isFrozen() ? "Frozen" : "Active";
                System.out.println((i + 1) + ". " + account.getAccountName() + " | Type: " + account.getAccountType() + " | Balance: $" + account.getBalance() + " | Status: " + status);
            }

            System.out.println((accounts.size() + 1) + ". Exit Account List");
            System.out.print("Enter selection: ");

            int selection = keyboardInput.nextInt();

            if (selection == accounts.size() + 1) {
                return;
            }

            if (selection < 1 || selection > accounts.size()) {
                System.out.println("Invalid selection.");
                continue;
            }

            adminAccountActionMenu(selection - 1);
        }
    }

    public void adminAccountActionMenu(int accountIndex) {
        BankAccount account = bank.getAccounts().get(accountIndex);

        while (true) {
            System.out.println();
            System.out.println("=== Admin Action Menu ===");
            System.out.println("Account: " + account.getAccountName());
            System.out.println("Type: " + account.getAccountType());
            System.out.println("Balance: $" + account.getBalance());
            System.out.println("Status: " + (account.isFrozen() ? "Frozen" : "Active"));
            System.out.println("1. Freeze Account");
            System.out.println("2. Unfreeze Account");
            System.out.println("3. Collect Fee");
            System.out.println("4. Add Interest");
            if (account.getAccountType().equals("Saving")) {
                System.out.println("5. Add interest to saving account (" + (bank.getSavingsInterestRate() * 100) + "%)");
                System.out.println("6. Back to Account List");
            } else {
                System.out.println("5. Back to Account List");
            }

            int maxSelection = 5;
            if (account.getAccountType().equals("Saving")) {
                maxSelection = 6;
            }
            int selection = getUserSelection(maxSelection);

            switch (selection) {
                case 1:
                    bank.freezeAccount(accountIndex);
                    System.out.println("Account frozen successfully.");
                    break;
                case 2:
                    bank.unfreezeAccount(accountIndex);
                    System.out.println("Account unfrozen successfully.");
                    break;
                case 3:
                    System.out.print("Enter fee amount: ");
                    double feeAmount = keyboardInput.nextDouble();
                    try {
                        bank.collectFeeFromAccount(accountIndex, feeAmount);
                        System.out.println("Fee collected successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Enter interest amount: ");
                    double interestAmount = keyboardInput.nextDouble();
                    try {
                        bank.addInterestToAccount(accountIndex, interestAmount);
                        System.out.println("Interest payment added successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    if (account.getAccountType().equals("Saving")) {
                        try {
                            bank.addInterestToSavingAccount(accountIndex);
                            System.out.println("Saving account interest added successfully.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    return;
                case 6:
                    return;
                default:
                    System.out.println("Invalid selection.");
                    break;
            }

            account = bank.getAccounts().get(accountIndex);
        }
    }

    public void updateSavingsInterestRate() {
        System.out.print("Enter new savings interest rate: ");
        double newSavingsInterestRate = keyboardInput.nextDouble();

        try {
            bank.updateSavingsInterestRate(newSavingsInterestRate);
            System.out.println("Savings interest rate updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
