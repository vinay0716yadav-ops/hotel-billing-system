package com.hotel.billing.model;

public enum RoomType {
    STANDARD("Standard Room", 99.00),
    DELUXE("Deluxe Room", 159.00),
    SUITE("Executive Suite", 249.00),
    PRESIDENTIAL("Presidential Suite", 499.00);

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
