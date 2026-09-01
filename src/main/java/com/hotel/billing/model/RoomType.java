package com.hotel.billing.model;

public enum RoomType {
    STANDARD("Standard Room", 2499.00),
    DELUXE("Deluxe Room", 4999.00),
    SUITE("Executive Suite", 8999.00),
    PRESIDENTIAL("Presidential Suite", 19999.00);

    private final String displayName;
    private final double basePricePerNight;

    RoomType(String displayName, double basePricePerNight) {
        this.displayName = displayName;
        this.basePricePerNight = basePricePerNight;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getBasePricePerNight() {
        return basePricePerNight;
    }
}
