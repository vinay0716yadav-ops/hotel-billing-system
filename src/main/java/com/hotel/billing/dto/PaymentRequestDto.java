package com.hotel.billing.dto;

import com.hotel.billing.model.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public class PaymentRequestDto {

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    private String transactionId;
    private String notes;

    public PaymentRequestDto() {}

    public PaymentRequestDto(PaymentMethod paymentMethod, String transactionId, String notes) {
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.notes = notes;
    }

    public static PaymentRequestDtoBuilder builder() {
        return new PaymentRequestDtoBuilder();
    }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public static class PaymentRequestDtoBuilder {
        private PaymentMethod paymentMethod;
        private String transactionId;
        private String notes;

        public PaymentRequestDtoBuilder paymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public PaymentRequestDtoBuilder transactionId(String transactionId) { this.transactionId = transactionId; return this; }
        public PaymentRequestDtoBuilder notes(String notes) { this.notes = notes; return this; }
        public PaymentRequestDto build() {
            return new PaymentRequestDto(paymentMethod, transactionId, notes);
        }
    }
}
