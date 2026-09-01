package com.hotel.billing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bill_items")
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    @JsonIgnore
    private Bill bill;

    @Column(nullable = false)
    private String itemName;

    @Enumerated(EnumType.STRING)
    private ServiceCategory category;

    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private LocalDateTime serviceDate;

    public BillItem() {}

    public BillItem(Long id, Bill bill, String itemName, ServiceCategory category, int quantity, double unitPrice, double totalPrice, LocalDateTime serviceDate) {
        this.id = id;
        this.bill = bill;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.serviceDate = serviceDate;
    }

    public static BillItemBuilder builder() {
        return new BillItemBuilder();
    }

    @PrePersist
    @PreUpdate
    protected void calculateTotal() {
        this.totalPrice = Math.round(this.quantity * this.unitPrice * 100.0) / 100.0;
        if (this.serviceDate == null) {
            this.serviceDate = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Bill getBill() { return bill; }
    public void setBill(Bill bill) { this.bill = bill; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public ServiceCategory getCategory() { return category; }
    public void setCategory(ServiceCategory category) { this.category = category; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public LocalDateTime getServiceDate() { return serviceDate; }
    public void setServiceDate(LocalDateTime serviceDate) { this.serviceDate = serviceDate; }

    public static class BillItemBuilder {
        private Long id;
        private Bill bill;
        private String itemName;
        private ServiceCategory category;
        private int quantity;
        private double unitPrice;
        private double totalPrice;
        private LocalDateTime serviceDate;

        public BillItemBuilder id(Long id) { this.id = id; return this; }
        public BillItemBuilder bill(Bill bill) { this.bill = bill; return this; }
        public BillItemBuilder itemName(String itemName) { this.itemName = itemName; return this; }
        public BillItemBuilder category(ServiceCategory category) { this.category = category; return this; }
        public BillItemBuilder quantity(int quantity) { this.quantity = quantity; return this; }
        public BillItemBuilder unitPrice(double unitPrice) { this.unitPrice = unitPrice; return this; }
        public BillItemBuilder totalPrice(double totalPrice) { this.totalPrice = totalPrice; return this; }
        public BillItemBuilder serviceDate(LocalDateTime serviceDate) { this.serviceDate = serviceDate; return this; }
        public BillItem build() {
            return new BillItem(id, bill, itemName, category, quantity, unitPrice, totalPrice, serviceDate);
        }
    }
}
