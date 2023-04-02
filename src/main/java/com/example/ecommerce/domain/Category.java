package com.example.ecommerce.domain;

public enum Category {
    TOYS, ELECTRONICS, CLOTHES, BOOKS, HEALTH;

    private Category from(String val) {
        switch (val) {
            case ("TOYS"):
                return TOYS;
            case ("ELECTRONICS"):
                return ELECTRONICS;
            case ("CLOTHES"):
                return CLOTHES;
            case ("BOOKS"):
                return BOOKS;
            case ("HEALTH"):
                return HEALTH;
            default:
                return null;
        }
    }
}
