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
        int nDigits = cardNumber.length();

        int nSum = 0;
        boolean isSecond = false;

        for (int i = nDigits - 1; i >= 0; i--) {

            int d = cardNumber.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }
}
