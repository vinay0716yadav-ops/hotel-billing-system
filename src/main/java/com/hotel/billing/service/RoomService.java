package com.hotel.billing.service;

import com.hotel.billing.dto.RoomRequestDto;
import com.hotel.billing.model.Room;
import com.hotel.billing.model.RoomStatus;
import com.hotel.billing.model.RoomType;
import com.hotel.billing.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByStatus(RoomStatus.AVAILABLE);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Room not found with ID: " + id));
    }

    public Room createRoom(RoomRequestDto dto) {
        roomRepository.findByRoomNumber(dto.getRoomNumber()).ifPresent(r -> {
            throw new IllegalArgumentException("Room number " + dto.getRoomNumber() + " already exists.");
        });

        Room room = Room.builder()
                .roomNumber(dto.getRoomNumber())
                .roomType(dto.getRoomType())
                .pricePerNight(dto.getPricePerNight() > 0 ? dto.getPricePerNight() : dto.getRoomType().getBasePricePerNight())
                .status(dto.getStatus() != null ? dto.getStatus() : RoomStatus.AVAILABLE)
                .capacity(dto.getCapacity() > 0 ? dto.getCapacity() : 2)
                .floor(dto.getFloor() > 0 ? dto.getFloor() : 1)
                .features(dto.getFeatures())
                .build();

        return roomRepository.save(room);
    }

    public Room updateRoomStatus(Long id, RoomStatus status) {
        Room room = getRoomById(id);
        room.setStatus(status);
        return roomRepository.save(room);
    }
}
