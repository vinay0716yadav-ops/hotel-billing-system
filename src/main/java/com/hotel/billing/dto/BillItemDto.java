package com.hotel.billing.dto;

import com.hotel.billing.model.ServiceCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class BillItemDto {

    @NotBlank(message = "Item name is required")
    private String itemName;

    private ServiceCategory category;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity = 1;

    @Positive(message = "Unit price must be positive")
    private double unitPrice;

    public BillItemDto() {}

    public BillItemDto(String itemName, ServiceCategory category, int quantity, double unitPrice) {
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public static BillItemDtoBuilder builder() {
        return new BillItemDtoBuilder();
    }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public ServiceCategory getCategory() { return category; }
    public void setCategory(ServiceCategory category) { this.category = category; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public static class BillItemDtoBuilder {
        private String itemName;
        private ServiceCategory category;
        private int quantity = 1;
        private double unitPrice;

        public BillItemDtoBuilder itemName(String itemName) { this.itemName = itemName; return this; }
        public BillItemDtoBuilder category(ServiceCategory category) { this.category = category; return this; }
        public BillItemDtoBuilder quantity(int quantity) { this.quantity = quantity; return this; }
        public BillItemDtoBuilder unitPrice(double unitPrice) { this.unitPrice = unitPrice; return this; }
        public BillItemDto build() {
            return new BillItemDto(itemName, category, quantity, unitPrice);
        }
    }
}
