package com.example.ecommerce.domain;
public enum Category {
    BEAUTY,
    BOOKS,
    CLOTHING,
    ELECTRONICS,
    FOOD,
    FURNITURE,
    HEALTH,
    HOME,
    JEWELRY,
    SPORTS,
    TOYs,
    TRAVEL,
    SHOES,
    BAGS;


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
            case "TOYS":
                return TOYs;
            case "TRAVEL":
                return TRAVEL;
            case "SHOES":
                return SHOES;
            case "BAGS":
                return BAGS;
            default:
                return null;
        }
    }
}