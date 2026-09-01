package com.hotel.billing.dto;

import com.hotel.billing.model.RoomStatus;
import com.hotel.billing.model.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RoomRequestDto {

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotNull(message = "Room type is required")
    private RoomType roomType;

    @Positive(message = "Price must be positive")
    private double pricePerNight;

    private RoomStatus status;
    private int capacity = 2;
    private int floor = 1;
    private String features;

    public RoomRequestDto() {}

    public RoomRequestDto(String roomNumber, RoomType roomType, double pricePerNight, RoomStatus status, int capacity, int floor, String features) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.status = status;
        this.capacity = capacity;
        this.floor = floor;
        this.features = features;
    }

    public static RoomRequestDtoBuilder builder() {
        return new RoomRequestDtoBuilder();
    }

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

    public static class RoomRequestDtoBuilder {
        private String roomNumber;
        private RoomType roomType;
        private double pricePerNight;
        private RoomStatus status;
        private int capacity = 2;
        private int floor = 1;
        private String features;

        public RoomRequestDtoBuilder roomNumber(String roomNumber) { this.roomNumber = roomNumber; return this; }
        public RoomRequestDtoBuilder roomType(RoomType roomType) { this.roomType = roomType; return this; }
        public RoomRequestDtoBuilder pricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; return this; }
        public RoomRequestDtoBuilder status(RoomStatus status) { this.status = status; return this; }
        public RoomRequestDtoBuilder capacity(int capacity) { this.capacity = capacity; return this; }
        public RoomRequestDtoBuilder floor(int floor) { this.floor = floor; return this; }
        public RoomRequestDtoBuilder features(String features) { this.features = features; return this; }
        public RoomRequestDto build() {
            return new RoomRequestDto(roomNumber, roomType, pricePerNight, status, capacity, floor, features);
        }
    }
}
