package test;

import main.BankAccount;
import main.MainMenu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            // do nothing, test passes
        }
    }

    @Test
    public void testWithdraw() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        testAccount.withdraw(30);
        assertEquals(20, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testWithdrawWhenNoMoney() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.withdraw(30);
            fail();
        } catch (IllegalArgumentException e) {
            // do nothing, test passes
        }
    }

    @Test
    public void testOverWithdraw() {
        BankAccount testAccount = new BankAccount();
        try {
            testAccount.deposit(20);
            testAccount.withdraw(30);
            fail();
        } catch (IllegalArgumentException e) {
            // do nothing, test passes
        }
    }

    @Test
    public void testCheckEmptyBalance() {
        BankAccount testAccount = new BankAccount();
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testCheckBalanceAfterDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testTransactionHistoryAfterDeposit() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        assertEquals(1, account.getTransactionHistory().size());
        assertEquals("Deposited: $100.0", account.getTransactionHistory().get(0));
    }

    @Test
    public void testTransactionHistoryAfterWithdraw() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        account.withdraw(40);
        assertEquals(2, account.getTransactionHistory().size());
        assertEquals("Withdrew: $40.0", account.getTransactionHistory().get(1));
    }

    @Test
    public void testDefaultAccountName() {
        BankAccount testAccount = new BankAccount();
        assertEquals("Default", testAccount.getAccountName());
    }

    @Test
    public void testCustomAccountName() {
        BankAccount testAccount = new BankAccount("nailong");
        assertEquals("nailong", testAccount.getAccountName());
    }

    @Test
    public void testNewAccountBalanceIsZero() {
        BankAccount testAccount = new BankAccount("nailong");
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testNewAccountTransactionHistoryIsEmpty() {
        BankAccount testAccount = new BankAccount("nailong");
        assertEquals(0, testAccount.getTransactionHistory().size());
    }

    @Test
    public void testDepositWithCustomAccount() {
        BankAccount testAccount = new BankAccount("nailong");
        testAccount.deposit(200);
        assertEquals(200, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testMultipleAccountsIndependence() {
        BankAccount acc1 = new BankAccount("nailong");
        BankAccount acc2 = new BankAccount("nailong2");

        acc1.deposit(100);
        acc2.deposit(50);

        assertEquals(100, acc1.getBalance(), 0.01);
        assertEquals(50, acc2.getBalance(), 0.01);
    }

    @Test
    public void testCannotCloseOnlyRemainingAccount() {
        MainMenu menu = new MainMenu();
        menu.closeCurrentAccount();
        List<BankAccount> accounts = menu.getAccounts();
        assertEquals(1, accounts.size());
        assertEquals("Default", menu.getCurrentAccount().getAccountName());
    }

    @Test
    public void testCloseAccountSuccessful() {
        MainMenu menu = new MainMenu();
        menu.getAccounts().add(new BankAccount("nailong"));
        menu.setCurrentAccountIndex(1);

        menu.closeCurrentAccount();

        assertEquals(1, menu.getAccounts().size());
        assertEquals("Default", menu.getCurrentAccount().getAccountName());
    }

    @Test
    public void testCannotCloseAccountWithRemainingBalance() {
        MainMenu menu = new MainMenu();
        menu.getAccounts().add(new BankAccount("nailong"));
        menu.setCurrentAccountIndex(1);
        menu.getCurrentAccount().deposit(50);

        menu.closeCurrentAccount();

        assertEquals(2, menu.getAccounts().size());
        assertEquals("nailong", menu.getCurrentAccount().getAccountName());
    }

    @Test
    public void testTransferBetweenAccounts() {
        MainMenu menu = new MainMenu();
        menu.getAccounts().add(new BankAccount("nailong"));
        menu.getCurrentAccount().deposit(100);
        menu.transferBetweenAccounts(1, 40);
        assertEquals(60, menu.getCurrentAccount().getBalance(), 0.01);
        assertEquals(40, menu.getAccounts().get(1).getBalance(), 0.01);
    }

    @Test
    public void testCannotTransferToSameAccount() {
        MainMenu menu = new MainMenu();
        menu.getCurrentAccount().deposit(100);

        try {
            menu.transferBetweenAccounts(0, 40);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testTransferBetweenAccountsInvalidTarget() {
        MainMenu menu = new MainMenu();
        menu.getCurrentAccount().deposit(100);

        try {
            menu.transferBetweenAccounts(5, 40);
            fail();
        } catch (IllegalArgumentException e) {
            // Test passes
        }
    }

    @Test
    public void testCollectFeeFromExistingAccount() {
        MainMenu menu = new MainMenu();
        menu.getCurrentAccount().deposit(100);

        menu.collectFeeFromAccount(0, 10);

        assertEquals(90, menu.getCurrentAccount().getBalance(), 0.01);
        assertEquals(2, menu.getCurrentAccount().getTransactionHistory().size());
        assertEquals("Collected fee: $10.0", menu.getCurrentAccount().getTransactionHistory().get(1));
    }
}
