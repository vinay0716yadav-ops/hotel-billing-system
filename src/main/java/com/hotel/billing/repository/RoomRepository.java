package com.hotel.billing.repository;

import com.hotel.billing.model.Room;
import com.hotel.billing.model.RoomStatus;
import com.hotel.billing.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
    List<Room> findByStatus(RoomStatus status);
    List<Room> findByRoomType(RoomType roomType);
    long countByStatus(RoomStatus status);
}
