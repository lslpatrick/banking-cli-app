package test;

import main.Bank;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

public class CustomerPinTest {

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