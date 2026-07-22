package main;

import java.util.Scanner;

public class InputHelper {
    private final Scanner keyboardInput;

    public InputHelper(Scanner keyboardInput) {
        this.keyboardInput = keyboardInput;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);

        if (!keyboardInput.hasNextLine()) {
            throw new IllegalStateException("No more input available.");
        }

        return keyboardInput.nextLine().trim();
    }

    public String readRequiredLine(String prompt, String errorMessage) {
        while (true) {
            String input = readLine(prompt);
            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(errorMessage);
        }
    }

    public int readIntInRange(String prompt, int min, int max) {
        while (true) {
            String input = readLine(prompt);

            try {
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public double readPositiveDouble(String prompt) {
        while (true) {
            String input = readLine(prompt);

            try {
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Please enter a positive amount.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
