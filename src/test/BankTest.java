package test;

import main.Bank;
import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

public class BankTest {

    @Test
    public void testCannotCloseOnlyRemainingAccount() {
        Bank bank = new Bank();

        try {
            bank.closeCurrentAccount();
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }

        assertEquals(1, bank.getAccounts().size());
        assertEquals("Default", bank.getCurrentAccount().getAccountName());
    }

    @Test
    public void testCloseAccountSuccessful() {
        Bank bank = new Bank();
        bank.createAccount("nailong", "Checking");
        bank.setCurrentAccountIndex(1);

        bank.closeCurrentAccount();

        assertEquals(1, bank.getAccounts().size());
        assertEquals("Default", bank.getCurrentAccount().getAccountName());
    }

    @Test
    public void testCannotCloseAccountWithRemainingBalance() {
        Bank bank = new Bank();
        bank.createAccount("nailong", "Checking");
        bank.setCurrentAccountIndex(1);
        bank.getCurrentAccount().deposit(50);

        try {
            bank.closeCurrentAccount();
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }

        assertEquals(2, bank.getAccounts().size());
        assertEquals("nailong", bank.getCurrentAccount().getAccountName());
    }

    @Test
    public void testTransferBetweenAccounts() {
        Bank bank = new Bank();
        bank.createAccount("nailong", "Checking");
        bank.getCurrentAccount().deposit(100);

        bank.transferBetweenAccounts(1, 40);

        assertEquals(60, bank.getCurrentAccount().getBalance(), 0.01);
        assertEquals(40, bank.getAccounts().get(1).getBalance(), 0.01);
    }

    @Test
    public void testCannotTransferToSameAccount() {
        Bank bank = new Bank();
        bank.getCurrentAccount().deposit(100);

        try {
            bank.transferBetweenAccounts(0, 40);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testTransferBetweenAccountsInvalidTarget() {
        Bank bank = new Bank();
        bank.getCurrentAccount().deposit(100);

        try {
            bank.transferBetweenAccounts(5, 40);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testTransferCannotUseOverdrawProtection() {
        Bank bank = new Bank();
        bank.createAccount("nailong", "Checking");
        bank.getCurrentAccount().deposit(20);

        try {
            bank.transferBetweenAccounts(1, 30);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }

        assertEquals(20, bank.getCurrentAccount().getBalance(), 0.01);
        assertEquals(0, bank.getAccounts().get(1).getBalance(), 0.01);
    }

    @Test
    public void testCollectFeeFromExistingAccount() {
        Bank bank = new Bank();
        bank.getCurrentAccount().deposit(100);

        bank.collectFeeFromAccount(0, 10);

        assertEquals(90, bank.getCurrentAccount().getBalance(), 0.01);
        assertEquals(2, bank.getCurrentAccount().getTransactionHistory().size());
        assertEquals("Collected fee: $10.0", bank.getCurrentAccount().getTransactionHistory().get(1));
    }

    @Test
    public void testAddInterestToExistingAccount() {
        Bank bank = new Bank();
        bank.getCurrentAccount().deposit(100);

        bank.addInterestToAccount(0, 5);

        assertEquals(105, bank.getCurrentAccount().getBalance(), 0.01);
        assertEquals(2, bank.getCurrentAccount().getTransactionHistory().size());
        assertEquals("Interest payment: $5.0",
                bank.getCurrentAccount().getTransactionHistory().get(1));
    }

    @Test
    public void testAddInterestToSavingAccount() {
        Bank bank = new Bank();
        bank.createAccount("nailong", "Saving");
        bank.setCurrentAccountIndex(1);
        bank.getCurrentAccount().deposit(100);

        bank.addInterestToSavingAccount(1);

        assertEquals(102, bank.getCurrentAccount().getBalance(), 0.01);
        assertEquals(2, bank.getCurrentAccount().getTransactionHistory().size());
        assertEquals("Interest payment: $2.0",
                bank.getCurrentAccount().getTransactionHistory().get(1));
    }

    @Test
    public void testCannotAddSavingInterestToCheckingAccount() {
        Bank bank = new Bank();
        bank.getCurrentAccount().deposit(100);

        try {
            bank.addInterestToSavingAccount(0);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }

        assertEquals(100, bank.getCurrentAccount().getBalance(), 0.01);
    }

    @Test
    public void testCreateAccount() {
        Bank bank = new Bank();
        bank.createAccount("nailong", "Saving");

        assertEquals(2, bank.getAccounts().size());
        assertEquals("nailong", bank.getAccounts().get(1).getAccountName());
        assertEquals("Saving", bank.getAccounts().get(1).getAccountType());
    }

    @Test
    public void testFreezeAccountFromBank() {
        Bank bank = new Bank();
        bank.createAccount("nailong", "Checking");

        bank.freezeAccount(1);

        assertTrue(bank.getAccounts().get(1).isFrozen());
    }

    @Test
    public void testUnfreezeAccountFromBank() {
        Bank bank = new Bank();
        bank.createAccount("nailong", "Checking");
        bank.freezeAccount(1);

        bank.unfreezeAccount(1);

        assertFalse(bank.getAccounts().get(1).isFrozen());
    }

    @Test
    public void testChangeCurrentAccount() {
        Bank bank = new Bank();
        bank.createAccount("nailong", "Saving");

        bank.changeCurrentAccount(1);

        assertEquals("nailong", bank.getCurrentAccount().getAccountName());
    }

    @Test
    public void testChangeCurrentAccountInvalidIndex() {
        Bank bank = new Bank();

        try {
            bank.changeCurrentAccount(5);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testCollectFeeInsufficientBalance() {
        Bank bank = new Bank();

        try {
            bank.collectFeeFromAccount(0, 10);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }
    }

    @Test
    public void testCollectFeeCannotUseOverdrawProtection() {
        Bank bank = new Bank();
        bank.getCurrentAccount().withdrawWithOverdrawProtection(50);

        try {
            bank.collectFeeFromAccount(0, 10);
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }

        assertEquals(-50, bank.getCurrentAccount().getBalance(), 0.01);
    }

    @Test
    public void testSetCustomerPinSuccess() {
        Bank bank = new Bank();
        bank.setCustomerPin("1234");

        assertTrue(bank.hasCustomerPin());
        assertTrue(bank.verifyCustomerPin("1234"));
    }

    @Test
    public void testSetCustomerPinInvalidFormat() {
        Bank bank = new Bank();

        try {
            bank.setCustomerPin("12");
            fail();
        } catch (IllegalArgumentException e) {
            // test passes
        }

        assertFalse(bank.hasCustomerPin());
    }

    @Test
    public void testVerifyCustomerPinWrongPin() {
        Bank bank = new Bank();
        bank.setCustomerPin("1234");

        assertFalse(bank.verifyCustomerPin("9999"));
    }

    @Test
    public void testChangeCustomerPinSuccess() {
        Bank bank = new Bank();
        bank.setCustomerPin("1234");

        assertTrue(bank.changeCustomerPin("1234", "5678"));
        assertTrue(bank.verifyCustomerPin("5678"));
        assertFalse(bank.verifyCustomerPin("1234"));
    }
}
