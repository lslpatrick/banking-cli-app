package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
}
