package com.hotel.billing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String bookingReference;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @NotNull(message = "Check-in date is required")
    @Column(nullable = false)
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Min(value = 1, message = "At least 1 guest required")
    private int numberOfGuests;

    private double customRoomRate;
    private double extraBedCharge;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    private String specialRequests;
    private LocalDateTime createdAt;

    public Booking() {}

    public Booking(Long id, String bookingReference, Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests, double customRoomRate, double extraBedCharge, BookingStatus status, String specialRequests, LocalDateTime createdAt) {
        this.id = id;
        this.bookingReference = bookingReference;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.customRoomRate = customRoomRate;
        this.extraBedCharge = extraBedCharge;
        this.status = status;
        this.specialRequests = specialRequests;
        this.createdAt = createdAt;
    }

    public static BookingBuilder builder() {
        return new BookingBuilder();
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public long getStayDurationNights() {
        if (checkInDate == null || checkOutDate == null) {
            return 1;
        }
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return nights <= 0 ? 1 : nights;
    }

    public double getEffectiveRoomRate() {
        return customRoomRate > 0 ? customRoomRate : (room != null ? room.getPricePerNight() : 0.0);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
    public int getNumberOfGuests() { return numberOfGuests; }
    public void setNumberOfGuests(int numberOfGuests) { this.numberOfGuests = numberOfGuests; }
    public double getCustomRoomRate() { return customRoomRate; }
    public void setCustomRoomRate(double customRoomRate) { this.customRoomRate = customRoomRate; }
    public double getExtraBedCharge() { return extraBedCharge; }
    public void setExtraBedCharge(double extraBedCharge) { this.extraBedCharge = extraBedCharge; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static class BookingBuilder {
        private Long id;
        private String bookingReference;
        private Guest guest;
        private Room room;
        private LocalDate checkInDate;
        private LocalDate checkOutDate;
        private int numberOfGuests = 1;
        private double customRoomRate;
        private double extraBedCharge;
        private BookingStatus status;
        private String specialRequests;
        private LocalDateTime createdAt;

        public BookingBuilder id(Long id) { this.id = id; return this; }
        public BookingBuilder bookingReference(String bookingReference) { this.bookingReference = bookingReference; return this; }
        public BookingBuilder guest(Guest guest) { this.guest = guest; return this; }
        public BookingBuilder room(Room room) { this.room = room; return this; }
        public BookingBuilder checkInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; return this; }
        public BookingBuilder checkOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; return this; }
        public BookingBuilder numberOfGuests(int numberOfGuests) { this.numberOfGuests = numberOfGuests; return this; }
        public BookingBuilder customRoomRate(double customRoomRate) { this.customRoomRate = customRoomRate; return this; }
        public BookingBuilder extraBedCharge(double extraBedCharge) { this.extraBedCharge = extraBedCharge; return this; }
        public BookingBuilder status(BookingStatus status) { this.status = status; return this; }
        public BookingBuilder specialRequests(String specialRequests) { this.specialRequests = specialRequests; return this; }
        public BookingBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Booking build() {
            return new Booking(id, bookingReference, guest, room, checkInDate, checkOutDate, numberOfGuests, customRoomRate, extraBedCharge, status, specialRequests, createdAt);
        }
    }
}
