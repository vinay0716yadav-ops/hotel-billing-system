package com.hotel.billing.service;

import com.hotel.billing.dto.BookingRequestDto;
import com.hotel.billing.model.*;
import com.hotel.billing.repository.BookingRepository;
import com.hotel.billing.repository.GuestRepository;
import com.hotel.billing.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private static final AtomicInteger bookingSeq = new AtomicInteger(1001);

    public BookingService(BookingRepository bookingRepository,
                          GuestRepository guestRepository,
                          RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getActiveBookings() {
        return bookingRepository.findByStatus(BookingStatus.ACTIVE);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Booking not found with ID: " + id));
    }

    public Booking createBooking(BookingRequestDto dto) {
        // Find or create guest
        Guest guest = guestRepository.findByEmail(dto.getGuestEmail())
                .orElseGet(() -> Guest.builder()
                        .fullName(dto.getGuestFullName())
                        .email(dto.getGuestEmail())
                        .phone(dto.getGuestPhone())
                        .idProofType(dto.getIdProofType())
                        .idProofNumber(dto.getIdProofNumber())
                        .address(dto.getGuestAddress())
                        .city(dto.getGuestCity())
                        .country(dto.getGuestCountry())
                        .build());
        guest = guestRepository.save(guest);

        // Fetch room
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new NoSuchElementException("Room not found with ID: " + dto.getRoomId()));

        if (room.getStatus() == RoomStatus.OCCUPIED) {
            throw new IllegalStateException("Room " + room.getRoomNumber() + " is already occupied.");
        }

        // Set room status to occupied
        room.setStatus(RoomStatus.OCCUPIED);
        roomRepository.save(room);

        // Create booking
        String reference = String.format("BK-%s-%04d",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM")),
                bookingSeq.getAndIncrement());

        Booking booking = Booking.builder()
                .bookingReference(reference)
                .guest(guest)
                .room(room)
                .checkInDate(dto.getCheckInDate())
                .checkOutDate(dto.getCheckOutDate())
                .numberOfGuests(dto.getNumberOfGuests() > 0 ? dto.getNumberOfGuests() : 1)
                .customRoomRate(dto.getCustomRoomRate())
                .extraBedCharge(dto.getExtraBedCharge())
                .status(BookingStatus.ACTIVE)
                .specialRequests(dto.getSpecialRequests())
                .build();

        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        booking.setStatus(BookingStatus.CANCELLED);
        Room room = booking.getRoom();
        if (room != null) {
            room.setStatus(RoomStatus.AVAILABLE);
            roomRepository.save(room);
        }
        bookingRepository.save(booking);
    }
}
