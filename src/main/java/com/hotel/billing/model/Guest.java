package com.hotel.billing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    private String fullName;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Column(nullable = false)
    private String phone;

    private String idProofType;
    private String idProofNumber;
    private String address;
    private String city;
    private String country;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Guest() {}

    public Guest(Long id, String fullName, String email, String phone, String idProofType, String idProofNumber, String address, String city, String country, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.idProofType = idProofType;
        this.idProofNumber = idProofNumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.createdAt = createdAt;
    }

    public static GuestBuilder builder() {
        return new GuestBuilder();
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getIdProofType() { return idProofType; }
    public void setIdProofType(String idProofType) { this.idProofType = idProofType; }
    public String getIdProofNumber() { return idProofNumber; }
    public void setIdProofNumber(String idProofNumber) { this.idProofNumber = idProofNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static class GuestBuilder {
        private Long id;
        private String fullName;
        private String email;
        private String phone;
        private String idProofType;
        private String idProofNumber;
        private String address;
        private String city;
        private String country;
        private LocalDateTime createdAt;

        public GuestBuilder id(Long id) { this.id = id; return this; }
        public GuestBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public GuestBuilder email(String email) { this.email = email; return this; }
        public GuestBuilder phone(String phone) { this.phone = phone; return this; }
        public GuestBuilder idProofType(String idProofType) { this.idProofType = idProofType; return this; }
        public GuestBuilder idProofNumber(String idProofNumber) { this.idProofNumber = idProofNumber; return this; }
        public GuestBuilder address(String address) { this.address = address; return this; }
        public GuestBuilder city(String city) { this.city = city; return this; }
        public GuestBuilder country(String country) { this.country = country; return this; }
        public GuestBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Guest build() {
            return new Guest(id, fullName, email, phone, idProofType, idProofNumber, address, city, country, createdAt);
        }
    }
}
