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
    TOYS,
    TRAVEL,
    SHOES,
    BAGS,
    PETS,
    WATCHES,
    CARS_MOTORBIKES,
    CAMERAS,
    PHONES,
    OTHERS;


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
                return TOYS;
            case "TRAVEL":
                return TRAVEL;
            case "SHOES":
                return SHOES;
            case "BAGS":
                return BAGS;
            case "PETS":
                return PETS;
            case "WATCHES":
                return WATCHES;
            case "CARS_MOTORBIKES":
                return CARS_MOTORBIKES;
            case "CAMERAS":
                return CAMERAS;
            case "PHONES":
                return PHONES;
            case "OTHERS":
                return OTHERS;
            default:
                return null;
        }
    }
}