package test;


import java.util.Scanner;

import main.AdminMenu;
import main.Bank;

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
}