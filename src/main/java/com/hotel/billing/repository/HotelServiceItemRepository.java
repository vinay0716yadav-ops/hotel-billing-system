package com.hotel.billing.repository;

import com.hotel.billing.model.HotelServiceItem;
import com.hotel.billing.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelServiceItemRepository extends JpaRepository<HotelServiceItem, Long> {
    List<HotelServiceItem> findByAvailableTrue();
    List<HotelServiceItem> findByCategory(ServiceCategory category);
}
