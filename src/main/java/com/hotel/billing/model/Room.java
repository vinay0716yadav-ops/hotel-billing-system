package com.hotel.billing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room number is required")
    @Column(unique = true, nullable = false)
    private String roomNumber;

    @NotNull(message = "Room type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @Positive(message = "Price per night must be positive")
    @Column(nullable = false)
    private double pricePerNight;

    @NotNull(message = "Room status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status;

    private int capacity;
    private int floor;
    private String features;

    public Room() {}

    public Room(Long id, String roomNumber, RoomType roomType, double pricePerNight, RoomStatus status, int capacity, int floor, String features) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.status = status;
        this.capacity = capacity;
        this.floor = floor;
        this.features = features;
    }

    public static RoomBuilder builder() {
        return new RoomBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }
    public RoomStatus getStatus() { return status; }
    public void setStatus(RoomStatus status) { this.status = status; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }
    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }

    public static class RoomBuilder {
        private Long id;
        private String roomNumber;
        private RoomType roomType;
        private double pricePerNight;
        private RoomStatus status;
        private int capacity;
        private int floor;
        private String features;

        public RoomBuilder id(Long id) { this.id = id; return this; }
        public RoomBuilder roomNumber(String roomNumber) { this.roomNumber = roomNumber; return this; }
        public RoomBuilder roomType(RoomType roomType) { this.roomType = roomType; return this; }
        public RoomBuilder pricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; return this; }
        public RoomBuilder status(RoomStatus status) { this.status = status; return this; }
        public RoomBuilder capacity(int capacity) { this.capacity = capacity; return this; }
        public RoomBuilder floor(int floor) { this.floor = floor; return this; }
        public RoomBuilder features(String features) { this.features = features; return this; }
        public Room build() {
            return new Room(id, roomNumber, roomType, pricePerNight, status, capacity, floor, features);
        }
    }
}
