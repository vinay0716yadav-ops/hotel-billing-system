package com.hotel.billing.dto;

import com.hotel.billing.model.PaymentMethod;
import com.hotel.billing.model.PaymentStatus;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BillRequestDto {

    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    private List<BillItemDto> items = new ArrayList<>();
    private Double customTaxPercentage;
    private double discountPercentage;
    private double extraBedCharges;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private String paymentTransactionId;
    private String notes;

    public BillRequestDto() {}

    public BillRequestDto(Long bookingId, List<BillItemDto> items, Double customTaxPercentage, double discountPercentage, double extraBedCharges, PaymentStatus paymentStatus, PaymentMethod paymentMethod, String paymentTransactionId, String notes) {
        this.bookingId = bookingId;
        this.items = items != null ? items : new ArrayList<>();
        this.customTaxPercentage = customTaxPercentage;
        this.discountPercentage = discountPercentage;
        this.extraBedCharges = extraBedCharges;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.paymentTransactionId = paymentTransactionId;
        this.notes = notes;
    }

    public static BillRequestDtoBuilder builder() {
        return new BillRequestDtoBuilder();
    }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public List<BillItemDto> getItems() { return items; }
    public void setItems(List<BillItemDto> items) { this.items = items; }
    public Double getCustomTaxPercentage() { return customTaxPercentage; }
    public void setCustomTaxPercentage(Double customTaxPercentage) { this.customTaxPercentage = customTaxPercentage; }
    public double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }
    public double getExtraBedCharges() { return extraBedCharges; }
    public void setExtraBedCharges(double extraBedCharges) { this.extraBedCharges = extraBedCharges; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPaymentTransactionId() { return paymentTransactionId; }
    public void setPaymentTransactionId(String paymentTransactionId) { this.paymentTransactionId = paymentTransactionId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public static class BillRequestDtoBuilder {
        private Long bookingId;
        private List<BillItemDto> items = new ArrayList<>();
        private Double customTaxPercentage;
        private double discountPercentage;
        private double extraBedCharges;
        private PaymentStatus paymentStatus;
        private PaymentMethod paymentMethod;
        private String paymentTransactionId;
        private String notes;

        public BillRequestDtoBuilder bookingId(Long bookingId) { this.bookingId = bookingId; return this; }
        public BillRequestDtoBuilder items(List<BillItemDto> items) { this.items = items; return this; }
        public BillRequestDtoBuilder customTaxPercentage(Double customTaxPercentage) { this.customTaxPercentage = customTaxPercentage; return this; }
        public BillRequestDtoBuilder discountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; return this; }
        public BillRequestDtoBuilder extraBedCharges(double extraBedCharges) { this.extraBedCharges = extraBedCharges; return this; }
        public BillRequestDtoBuilder paymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; return this; }
        public BillRequestDtoBuilder paymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public BillRequestDtoBuilder paymentTransactionId(String paymentTransactionId) { this.paymentTransactionId = paymentTransactionId; return this; }
        public BillRequestDtoBuilder notes(String notes) { this.notes = notes; return this; }
        public BillRequestDto build() {
            return new BillRequestDto(bookingId, items, customTaxPercentage, discountPercentage, extraBedCharges, paymentStatus, paymentMethod, paymentTransactionId, notes);
        }
    }
}
