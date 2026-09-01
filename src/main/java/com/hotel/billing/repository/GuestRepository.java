package com.hotel.billing.repository;

import com.hotel.billing.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByEmail(String email);
    Optional<Guest> findByPhone(String phone);
    List<Guest> findByFullNameContainingIgnoreCase(String name);
}
