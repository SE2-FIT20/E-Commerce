package com.example.ecommerce.utils;

import java.security.SecureRandom;
import java.util.Random;

public class Utils {
    private static final String HEX_CHARS = "0123456789ABCDEF";
    private static final int HEX_LENGTH = 6;
    private static final int CODE_LENGTH = 8;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
    public static String generateAvatarLink(String name) {
        String color = getRandomHexColor();
        return String.format("https://ui-avatars.com/api/?name=%s&background=%s", name, color);
    }

    public static String getRandomHexColor() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(HEX_LENGTH);
        for (int i = 0; i < HEX_LENGTH; i++) {
            sb.append(HEX_CHARS.charAt(random.nextInt(HEX_CHARS.length())));
        }
        return sb.toString();
    }

    // Luhn algorithm
    public static boolean isValidCardNumber(String cardNumber) {
        // Step 1: Remove all non-digit characters from the card number
        String digitsOnly = cardNumber.replaceAll("[^0-9]", "");

        // Step 2: Check if the length of the card number is between 13 and 19 digits
        int length = digitsOnly.length();
        if (length < 13 || length > 19) {
            return false;
        }

        // Step 3: Calculate the Luhn checksum of the card number
        int sum = 0;
        boolean alternate = false;
        for (int i = length - 1; i >= 0; i--) {
            int digit = Integer.parseInt(digitsOnly.substring(i, i + 1));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        int checksum = sum % 10;
        if (checksum != 0) {
            checksum = 10 - checksum;
        }

        // Step 4: Compare the calculated checksum with the last digit of the card number
        int lastDigit = Integer.parseInt(digitsOnly.substring(length - 1, length));
        return checksum == lastDigit;
    }
}
