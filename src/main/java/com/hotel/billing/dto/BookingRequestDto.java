package com.hotel.billing.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class BookingRequestDto {

    @NotBlank(message = "Guest full name is required")
    private String guestFullName;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Guest email is required")
    private String guestEmail;

    @NotBlank(message = "Guest phone is required")
    private String guestPhone;

    private String idProofType;
    private String idProofNumber;
    private String guestAddress;
    private String guestCity;
    private String guestCountry;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "At least 1 guest required")
    private int numberOfGuests = 1;

    private double customRoomRate;
    private double extraBedCharge;
    private String specialRequests;

    public BookingRequestDto() {}

    public BookingRequestDto(String guestFullName, String guestEmail, String guestPhone, String idProofType, String idProofNumber, String guestAddress, String guestCity, String guestCountry, Long roomId, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests, double customRoomRate, double extraBedCharge, String specialRequests) {
        this.guestFullName = guestFullName;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
        this.idProofType = idProofType;
        this.idProofNumber = idProofNumber;
        this.guestAddress = guestAddress;
        this.guestCity = guestCity;
        this.guestCountry = guestCountry;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.customRoomRate = customRoomRate;
        this.extraBedCharge = extraBedCharge;
        this.specialRequests = specialRequests;
    }

    public static BookingRequestDtoBuilder builder() {
        return new BookingRequestDtoBuilder();
    }

    public String getGuestFullName() { return guestFullName; }
    public void setGuestFullName(String guestFullName) { this.guestFullName = guestFullName; }
    public String getGuestEmail() { return guestEmail; }
    public void setGuestEmail(String guestEmail) { this.guestEmail = guestEmail; }
    public String getGuestPhone() { return guestPhone; }
    public void setGuestPhone(String guestPhone) { this.guestPhone = guestPhone; }
    public String getIdProofType() { return idProofType; }
    public void setIdProofType(String idProofType) { this.idProofType = idProofType; }
    public String getIdProofNumber() { return idProofNumber; }
    public void setIdProofNumber(String idProofNumber) { this.idProofNumber = idProofNumber; }
    public String getGuestAddress() { return guestAddress; }
    public void setGuestAddress(String guestAddress) { this.guestAddress = guestAddress; }
    public String getGuestCity() { return guestCity; }
    public void setGuestCity(String guestCity) { this.guestCity = guestCity; }
    public String getGuestCountry() { return guestCountry; }
    public void setGuestCountry(String guestCountry) { this.guestCountry = guestCountry; }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
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
    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }

    public static class BookingRequestDtoBuilder {
        private String guestFullName;
        private String guestEmail;
        private String guestPhone;
        private String idProofType;
        private String idProofNumber;
        private String guestAddress;
        private String guestCity;
        private String guestCountry;
        private Long roomId;
        private LocalDate checkInDate;
        private LocalDate checkOutDate;
        private int numberOfGuests = 1;
        private double customRoomRate;
        private double extraBedCharge;
        private String specialRequests;

        public BookingRequestDtoBuilder guestFullName(String guestFullName) { this.guestFullName = guestFullName; return this; }
        public BookingRequestDtoBuilder guestEmail(String guestEmail) { this.guestEmail = guestEmail; return this; }
        public BookingRequestDtoBuilder guestPhone(String guestPhone) { this.guestPhone = guestPhone; return this; }
        public BookingRequestDtoBuilder idProofType(String idProofType) { this.idProofType = idProofType; return this; }
        public BookingRequestDtoBuilder idProofNumber(String idProofNumber) { this.idProofNumber = idProofNumber; return this; }
        public BookingRequestDtoBuilder guestAddress(String guestAddress) { this.guestAddress = guestAddress; return this; }
        public BookingRequestDtoBuilder guestCity(String guestCity) { this.guestCity = guestCity; return this; }
        public BookingRequestDtoBuilder guestCountry(String guestCountry) { this.guestCountry = guestCountry; return this; }
        public BookingRequestDtoBuilder roomId(Long roomId) { this.roomId = roomId; return this; }
        public BookingRequestDtoBuilder checkInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; return this; }
        public BookingRequestDtoBuilder checkOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; return this; }
        public BookingRequestDtoBuilder numberOfGuests(int numberOfGuests) { this.numberOfGuests = numberOfGuests; return this; }
        public BookingRequestDtoBuilder customRoomRate(double customRoomRate) { this.customRoomRate = customRoomRate; return this; }
        public BookingRequestDtoBuilder extraBedCharge(double extraBedCharge) { this.extraBedCharge = extraBedCharge; return this; }
        public BookingRequestDtoBuilder specialRequests(String specialRequests) { this.specialRequests = specialRequests; return this; }
        public BookingRequestDto build() {
            return new BookingRequestDto(guestFullName, guestEmail, guestPhone, idProofType, idProofNumber, guestAddress, guestCity, guestCountry, roomId, checkInDate, checkOutDate, numberOfGuests, customRoomRate, extraBedCharge, specialRequests);
        }
    }
}
