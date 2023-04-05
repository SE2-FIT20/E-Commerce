package com.example.ecommerce.domain;
public enum Category {
    BEAUTY, BOOKS, CLOTHING, ELECTRONICS, FOOD, FURNITURE, HEALTH, HOME, JEWELRY, SPORTS;

    public static Category fromString(String val) {
        switch (val.toUpperCase()) {
            case "BEAUTY":
                return BEAUTY;
            case "BOOKS":
                return BOOKS;
            case "CLOTHING":
                return CLOTHING;
            case "ELECTRONICS":
                return ELECTRONICS;
            case "FOOD":
                return FOOD;
            case "FURNITURE":
                return FURNITURE;
            case "HEALTH":
                return HEALTH;
            case "HOME":
                return HOME;
            case "JEWELRY":
                return JEWELRY;
            case "SPORTS":
                return SPORTS;
            default:
                return null;
        }
    }
}