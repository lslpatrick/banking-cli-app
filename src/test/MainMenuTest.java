package test;

import main.MainMenu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class MainMenuTest {

    @Test
    public void testCorrectAdminPassword() {
        MainMenu menu = new MainMenu();
        assertTrue(menu.isCorrectAdminPassword("0422"));
    }

    @Test
    public void testIncorrectAdminPassword() {
        MainMenu menu = new MainMenu();
        assertFalse(menu.isCorrectAdminPassword("wrong"));
    }

    @Test
    public void testEmptyAdminPassword() {
        MainMenu menu = new MainMenu();
        assertFalse(menu.isCorrectAdminPassword(""));
    }

    @Test
    public void testUpdateAdminPasswordSuccess() {
        MainMenu menu = new MainMenu();
        assertTrue(menu.updateAdminPassword("0422", "5678"));
        assertTrue(menu.isCorrectAdminPassword("5678"));
        assertFalse(menu.isCorrectAdminPassword("0422"));
    }

    @Test
    public void testUpdateAdminPasswordWrongCurrentPassword() {
        MainMenu menu = new MainMenu();
        assertFalse(menu.updateAdminPassword("wrong", "5678"));
        assertTrue(menu.isCorrectAdminPassword("0422"));
    }

    @Test
    public void testUpdateAdminPasswordEmptyNewPassword() {
        MainMenu menu = new MainMenu();
        assertFalse(menu.updateAdminPassword("0422", ""));
        assertTrue(menu.isCorrectAdminPassword("0422"));
    }
}