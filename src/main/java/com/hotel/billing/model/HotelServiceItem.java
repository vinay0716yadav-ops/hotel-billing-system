package com.hotel.billing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "hotel_services")
public class HotelServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Service name is required")
    @Column(nullable = false)
    private String name;

    private String description;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceCategory category;

    @Positive(message = "Price must be positive")
    @Column(nullable = false)
    private double unitPrice;

    private boolean available = true;

    public HotelServiceItem() {}

    public HotelServiceItem(Long id, String name, String description, ServiceCategory category, double unitPrice, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.unitPrice = unitPrice;
        this.available = available;
    }

    public static HotelServiceItemBuilder builder() {
        return new HotelServiceItemBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ServiceCategory getCategory() { return category; }
    public void setCategory(ServiceCategory category) { this.category = category; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public static class HotelServiceItemBuilder {
        private Long id;
        private String name;
        private String description;
        private ServiceCategory category;
        private double unitPrice;
        private boolean available = true;

        public HotelServiceItemBuilder id(Long id) { this.id = id; return this; }
        public HotelServiceItemBuilder name(String name) { this.name = name; return this; }
        public HotelServiceItemBuilder description(String description) { this.description = description; return this; }
        public HotelServiceItemBuilder category(ServiceCategory category) { this.category = category; return this; }
        public HotelServiceItemBuilder unitPrice(double unitPrice) { this.unitPrice = unitPrice; return this; }
        public HotelServiceItemBuilder available(boolean available) { this.available = available; return this; }
        public HotelServiceItem build() {
            return new HotelServiceItem(id, name, description, category, unitPrice, available);
        }
    }
}
