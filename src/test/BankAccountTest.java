package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
            // do nothing,test passes
        }
    }

    @Test
    public void testWithdrawWithOverdrawProtection() {
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        testAccount.deposit(20);
        boolean usedOverdrawProtection = testAccount.withdrawWithOverdrawProtection(70);

        assertTrue(usedOverdrawProtection);
        assertEquals(-50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testWithdrawWithOverdrawProtectionLimit() {
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        boolean usedOverdrawProtection = testAccount.withdrawWithOverdrawProtection(200);

        assertTrue(usedOverdrawProtection);
        assertEquals(-200, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testCannotWithdrawPastOverdrawProtectionLimit() {
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        try {
            testAccount.withdrawWithOverdrawProtection(201);
            fail();
        } catch (IllegalArgumentException e) {
            // do nothing, test passes
        }

        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testSavingCannotUseOverdrawProtection() {
        BankAccount testAccount = new BankAccount("nailong", "Saving");
        testAccount.deposit(20);
        try {
            testAccount.withdrawWithOverdrawProtection(30);
            fail();
        } catch (IllegalArgumentException e) {
            // do nothing, test passes。
        }

        assertEquals(20, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testWithdrawDoesNotUseOverdrawProtection() {
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        testAccount.deposit(20);
        try {
            testAccount.withdraw(30);
            fail();
        } catch (IllegalArgumentException e) {
            // do nothing, test passes
        }

        assertEquals(20, testAccount.getBalance(), 0.01);
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
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        assertEquals("nailong", testAccount.getAccountName());
    }

    @Test
    public void testNewAccountBalanceIsZero() {
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        assertEquals(0, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testNewAccountTransactionHistoryIsEmpty() {
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        assertEquals(0, testAccount.getTransactionHistory().size());
    }

    @Test
    public void testDepositWithCustomAccount() {
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        testAccount.deposit(200);
        assertEquals(200, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testMultipleAccountsIndependence() {
        BankAccount acc1 = new BankAccount("nailong", "Checking");
        BankAccount acc2 = new BankAccount("nailong2", "Saving");

        acc1.deposit(100);
        acc2.deposit(50);

        assertEquals(100, acc1.getBalance(), 0.01);
        assertEquals(50, acc2.getBalance(), 0.01);
    }

    @Test
    public void testFreezeAccount() {
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        testAccount.freezeAccount();
        assertTrue(testAccount.isFrozen());
    }

    @Test
    public void testUnfreezeAccount() {
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        testAccount.freezeAccount();
        testAccount.unfreezeAccount();
        assertFalse(testAccount.isFrozen());
    }

    @Test
    public void testDepositWhenFrozen() {
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        testAccount.freezeAccount();

        try {
            testAccount.deposit(100);
            fail();
        } catch (IllegalStateException e) {
            // test passes
        }
    }

    @Test
    public void testWithdrawWhenFrozen() {
        BankAccount testAccount = new BankAccount("nailong", "Checking");
        testAccount.deposit(100);
        testAccount.freezeAccount();

        try {
            testAccount.withdraw(50);
            fail();
        } catch (IllegalStateException e) {
            // test passes
        }
    }

    @Test
    public void testDefaultAccountType() {
        BankAccount testAccount = new BankAccount();
        assertEquals("Checking", testAccount.getAccountType());
    }

    @Test
    public void testCustomAccountType() {
        BankAccount testAccount = new BankAccount("nailong", "Saving");
        assertEquals("Saving", testAccount.getAccountType());
    }
}
