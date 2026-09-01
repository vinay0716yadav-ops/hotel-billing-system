package com.hotel.billing.repository;

import com.hotel.billing.model.Booking;
import com.hotel.billing.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingReference(String bookingReference);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByGuestId(Long guestId);
    List<Booking> findByRoomId(Long roomId);
    long countByStatus(BookingStatus status);
}
