package com.hotel.billing.model;

public enum ServiceCategory {
    ROOM_SERVICE("Room Service & Dining"),
    BEVERAGES("Beverages & Bar"),
    LAUNDRY("Laundry & Dry Cleaning"),
    SPA_WELLNESS("Spa & Wellness"),
    TRANSPORT("Airport & Local Transport"),
    EXTRA_AMENITIES("Extra Amenities & Services");

    private final String displayName;

    ServiceCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
