package com.hotel.billing.service;

import com.hotel.billing.model.HotelServiceItem;
import com.hotel.billing.model.ServiceCategory;
import com.hotel.billing.repository.HotelServiceItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HotelServiceItemService {

    private final HotelServiceItemRepository repository;

    public HotelServiceItemService(HotelServiceItemRepository repository) {
        this.repository = repository;
    }

    public List<HotelServiceItem> getAllAvailableServices() {
        return repository.findByAvailableTrue();
    }

    public List<HotelServiceItem> getServicesByCategory(ServiceCategory category) {
        return repository.findByCategory(category);
    }

    public HotelServiceItem save(HotelServiceItem item) {
        return repository.save(item);
    }
}
