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
            //do nothing, test passes
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
            //do nothing, test passes
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
            //do nothing, test passes
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
    public void testCannotCloseOnlyRemainingAccount(){
        MainMenu menu = new MainMenu();

        menu.closeCurrentAccount();

        List<BankAccount> accounts = getAccounts(menu);
        assertEquals(1, accounts.size());
        assertEquals("Default", menu.getCurrentAccount().getAccountName());
    }

    @Test
    public void testCloseCurrentAccount(){
        MainMenu menu = new MainMenu();
        List<BankAccount> accounts = getAccounts(menu);

        accounts.add(new BankAccount("nailong"));
        setCurrentAccountIndex(menu, 1);

        menu.closeCurrentAccount();

        assertEquals(1, accounts.size());
        assertEquals("Default", menu.getCurrentAccount().getAccountName());
    }


}
