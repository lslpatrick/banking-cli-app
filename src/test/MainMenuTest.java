package test;


import java.io.ByteArrayInputStream;
import java.util.Scanner;

import main.AdminMenu;
import main.Bank;
import main.CustomerMenu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class MainMenuTest {

    @Test
    public void testCorrectAdminPassword() {
        AdminMenu menu = new AdminMenu(new Scanner(System.in), new Bank());
        assertTrue(menu.isCorrectAdminPassword("0422"));
    }

    @Test
    public void testIncorrectAdminPassword() {
        AdminMenu menu = new AdminMenu(new Scanner(System.in), new Bank());
        assertFalse(menu.isCorrectAdminPassword("wrong"));
    }

    @Test
    public void testEmptyAdminPassword() {
        AdminMenu menu = new AdminMenu(new Scanner(System.in), new Bank());
        assertFalse(menu.isCorrectAdminPassword(""));
    }

    @Test
    public void testUpdateAdminPasswordSuccess() {
        AdminMenu menu = new AdminMenu(new Scanner(System.in), new Bank());
        assertTrue(menu.updateAdminPassword("0422", "5678"));
        assertTrue(menu.isCorrectAdminPassword("5678"));
        assertFalse(menu.isCorrectAdminPassword("0422"));
    }

    @Test
    public void testUpdateAdminPasswordWrongCurrentPassword() {
        AdminMenu menu = new AdminMenu(new Scanner(System.in), new Bank());
        assertFalse(menu.updateAdminPassword("wrong", "5678"));
        assertTrue(menu.isCorrectAdminPassword("0422"));
    }

    @Test
    public void testUpdateAdminPasswordEmptyNewPassword() {
        AdminMenu menu = new AdminMenu(new Scanner(System.in), new Bank());
        assertFalse(menu.updateAdminPassword("0422", ""));
        assertTrue(menu.isCorrectAdminPassword("0422"));
    }

    @Test
    public void testCustomerServiceWithCorrectPin() {
        Bank bank = new Bank();
        bank.setCustomerPin("1234");
        Scanner scanner = new Scanner(new ByteArrayInputStream("1234\n50\n".getBytes()));
        CustomerMenu menu = new CustomerMenu(scanner, bank);

        menu.performDeposit();

        assertEquals(50, bank.getCurrentAccount().getBalance(), 0.01);
    }

    @Test
    public void testCustomerServiceWithIncorrectPinDoesNotRun() {
        Bank bank = new Bank();
        bank.setCustomerPin("1234");
        Scanner scanner = new Scanner(new ByteArrayInputStream("9999\n8888\n7777\n50\n".getBytes()));
        CustomerMenu menu = new CustomerMenu(scanner, bank);

        menu.performDeposit();

        assertEquals(0, bank.getCurrentAccount().getBalance(), 0.01);
    }
}
