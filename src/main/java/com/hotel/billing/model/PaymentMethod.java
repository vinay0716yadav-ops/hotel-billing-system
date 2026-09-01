package com.hotel.billing.model;

public enum PaymentMethod {
    CREDIT_CARD("Credit / Debit Card"),
    CASH("Cash"),
    UPI("UPI / Instant Transfer"),
    NET_BANKING("Net Banking"),
    CORPORATE_BILLING("Corporate Billing");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
