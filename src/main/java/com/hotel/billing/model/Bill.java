package com.hotel.billing.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String invoiceNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BillItem> items = new ArrayList<>();

    private long stayNights;
    private double roomRatePerNight;
    private double totalRoomCharge;
    private double totalServicesCharge;
    private double subtotal;
    private double taxPercentage;
    private double taxAmount;
    private double discountPercentage;
    private double discountAmount;
    private double netTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String paymentTransactionId;
    private String notes;
    private LocalDateTime issueDate;
    private LocalDateTime paidAt;

    public Bill() {}

    public Bill(Long id, String invoiceNumber, Booking booking, List<BillItem> items, long stayNights, double roomRatePerNight, double totalRoomCharge, double totalServicesCharge, double subtotal, double taxPercentage, double taxAmount, double discountPercentage, double discountAmount, double netTotal, PaymentStatus paymentStatus, PaymentMethod paymentMethod, String paymentTransactionId, String notes, LocalDateTime issueDate, LocalDateTime paidAt) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.booking = booking;
        this.items = items != null ? items : new ArrayList<>();
        this.stayNights = stayNights;
        this.roomRatePerNight = roomRatePerNight;
        this.totalRoomCharge = totalRoomCharge;
        this.totalServicesCharge = totalServicesCharge;
        this.subtotal = subtotal;
        this.taxPercentage = taxPercentage;
        this.taxAmount = taxAmount;
        this.discountPercentage = discountPercentage;
        this.discountAmount = discountAmount;
        this.netTotal = netTotal;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.paymentTransactionId = paymentTransactionId;
        this.notes = notes;
        this.issueDate = issueDate;
        this.paidAt = paidAt;
    }

    public static BillBuilder builder() {
        return new BillBuilder();
    }

    public void addItem(BillItem item) {
        items.add(item);
        item.setBill(this);
    }

    public void removeItem(BillItem item) {
        items.remove(item);
        item.setBill(null);
    }

    @PrePersist
    protected void onCreate() {
        if (this.issueDate == null) {
            this.issueDate = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public List<BillItem> getItems() { return items; }
    public void setItems(List<BillItem> items) { this.items = items; }
    public long getStayNights() { return stayNights; }
    public void setStayNights(long stayNights) { this.stayNights = stayNights; }
    public double getRoomRatePerNight() { return roomRatePerNight; }
    public void setRoomRatePerNight(double roomRatePerNight) { this.roomRatePerNight = roomRatePerNight; }
    public double getTotalRoomCharge() { return totalRoomCharge; }
    public void setTotalRoomCharge(double totalRoomCharge) { this.totalRoomCharge = totalRoomCharge; }
    public double getTotalServicesCharge() { return totalServicesCharge; }
    public void setTotalServicesCharge(double totalServicesCharge) { this.totalServicesCharge = totalServicesCharge; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public double getTaxPercentage() { return taxPercentage; }
    public void setTaxPercentage(double taxPercentage) { this.taxPercentage = taxPercentage; }
    public double getTaxAmount() { return taxAmount; }
    public void setTaxAmount(double taxAmount) { this.taxAmount = taxAmount; }
    public double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }
    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }
    public double getNetTotal() { return netTotal; }
    public void setNetTotal(double netTotal) { this.netTotal = netTotal; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPaymentTransactionId() { return paymentTransactionId; }
    public void setPaymentTransactionId(String paymentTransactionId) { this.paymentTransactionId = paymentTransactionId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }
    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }

    public static class BillBuilder {
        private Long id;
        private String invoiceNumber;
        private Booking booking;
        private List<BillItem> items = new ArrayList<>();
        private long stayNights;
        private double roomRatePerNight;
        private double totalRoomCharge;
        private double totalServicesCharge;
        private double subtotal;
        private double taxPercentage;
        private double taxAmount;
        private double discountPercentage;
        private double discountAmount;
        private double netTotal;
        private PaymentStatus paymentStatus;
        private PaymentMethod paymentMethod;
        private String paymentTransactionId;
        private String notes;
        private LocalDateTime issueDate;
        private LocalDateTime paidAt;

        public BillBuilder id(Long id) { this.id = id; return this; }
        public BillBuilder invoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; return this; }
        public BillBuilder booking(Booking booking) { this.booking = booking; return this; }
        public BillBuilder items(List<BillItem> items) { this.items = items; return this; }
        public BillBuilder stayNights(long stayNights) { this.stayNights = stayNights; return this; }
        public BillBuilder roomRatePerNight(double roomRatePerNight) { this.roomRatePerNight = roomRatePerNight; return this; }
        public BillBuilder totalRoomCharge(double totalRoomCharge) { this.totalRoomCharge = totalRoomCharge; return this; }
        public BillBuilder totalServicesCharge(double totalServicesCharge) { this.totalServicesCharge = totalServicesCharge; return this; }
        public BillBuilder subtotal(double subtotal) { this.subtotal = subtotal; return this; }
        public BillBuilder taxPercentage(double taxPercentage) { this.taxPercentage = taxPercentage; return this; }
        public BillBuilder taxAmount(double taxAmount) { this.taxAmount = taxAmount; return this; }
        public BillBuilder discountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; return this; }
        public BillBuilder discountAmount(double discountAmount) { this.discountAmount = discountAmount; return this; }
        public BillBuilder netTotal(double netTotal) { this.netTotal = netTotal; return this; }
        public BillBuilder paymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; return this; }
        public BillBuilder paymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public BillBuilder paymentTransactionId(String paymentTransactionId) { this.paymentTransactionId = paymentTransactionId; return this; }
        public BillBuilder notes(String notes) { this.notes = notes; return this; }
        public BillBuilder issueDate(LocalDateTime issueDate) { this.issueDate = issueDate; return this; }
        public BillBuilder paidAt(LocalDateTime paidAt) { this.paidAt = paidAt; return this; }
        public Bill build() {
            return new Bill(id, invoiceNumber, booking, items, stayNights, roomRatePerNight, totalRoomCharge, totalServicesCharge, subtotal, taxPercentage, taxAmount, discountPercentage, discountAmount, netTotal, paymentStatus, paymentMethod, paymentTransactionId, notes, issueDate, paidAt);
        }
    }
}
