package com.hotel.billing.config;

import com.hotel.billing.dto.BillItemDto;
import com.hotel.billing.dto.BillRequestDto;
import com.hotel.billing.dto.BookingRequestDto;
import com.hotel.billing.dto.RoomRequestDto;
import com.hotel.billing.model.*;
import com.hotel.billing.repository.HotelServiceItemRepository;
import com.hotel.billing.service.BillingService;
import com.hotel.billing.service.BookingService;
import com.hotel.billing.service.RoomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final RoomService roomService;
    private final HotelServiceItemRepository serviceRepository;
    private final BookingService bookingService;
    private final BillingService billingService;

    public DataInitializer(RoomService roomService,
                           HotelServiceItemRepository serviceRepository,
                           BookingService bookingService,
                           BillingService billingService) {
        this.roomService = roomService;
        this.serviceRepository = serviceRepository;
        this.bookingService = bookingService;
        this.billingService = billingService;
    }

    @Override
    public void run(String... args) {
        // 1. Seed Rooms
        Room r101 = roomService.createRoom(RoomRequestDto.builder()
                .roomNumber("101")
                .roomType(RoomType.STANDARD)
                .pricePerNight(99.00)
                .capacity(2)
                .floor(1)
                .features("Queen Bed, High-Speed Wi-Fi, Work Desk, Smart TV")
                .build());

        Room r102 = roomService.createRoom(RoomRequestDto.builder()
                .roomNumber("102")
                .roomType(RoomType.STANDARD)
                .pricePerNight(99.00)
                .capacity(2)
                .floor(1)
                .features("Twin Beds, City View, Wi-Fi, Coffee Maker")
                .build());

        Room r201 = roomService.createRoom(RoomRequestDto.builder()
                .roomNumber("201")
                .roomType(RoomType.DELUXE)
                .pricePerNight(159.00)
                .capacity(3)
                .floor(2)
                .features("King Bed, Ocean View, Private Balcony, Mini Bar")
                .build());

        Room r202 = roomService.createRoom(RoomRequestDto.builder()
                .roomNumber("202")
                .roomType(RoomType.DELUXE)
                .pricePerNight(159.00)
                .capacity(3)
                .floor(2)
                .features("King Bed, Garden View, Rain Shower, Espresso Machine")
                .build());

        Room r301 = roomService.createRoom(RoomRequestDto.builder()
                .roomNumber("301")
                .roomType(RoomType.SUITE)
                .pricePerNight(249.00)
                .capacity(4)
                .floor(3)
                .features("Master Bedroom, Living Room, Jacuzzi Tub, Panoramic Sea View")
                .build());

        Room r401 = roomService.createRoom(RoomRequestDto.builder()
                .roomNumber("401")
                .roomType(RoomType.PRESIDENTIAL)
                .pricePerNight(499.00)
                .capacity(5)
                .floor(4)
                .features("Penthouse Suite, Private Terrace, Personal Butler Service, Private Pool")
                .build());

        // 2. Seed Hotel Chargeable Services
        List<HotelServiceItem> services = List.of(
                HotelServiceItem.builder().name("Gourmet Breakfast Buffet").description("Continental & American spread").category(ServiceCategory.ROOM_SERVICE).unitPrice(25.00).available(true).build(),
                HotelServiceItem.builder().name("Chef's Special 3-Course Dinner").description("Fine dining in-room dining service").category(ServiceCategory.ROOM_SERVICE).unitPrice(65.00).available(true).build(),
                HotelServiceItem.builder().name("Club Sandwich & Fries").description("Classic hotel room service snack").category(ServiceCategory.ROOM_SERVICE).unitPrice(18.00).available(true).build(),
                HotelServiceItem.builder().name("Imported Wine Selection").description("Cabernet Sauvignon / Pinot Grigio 750ml").category(ServiceCategory.BEVERAGES).unitPrice(45.00).available(true).build(),
                HotelServiceItem.builder().name("Artisanal Mocktail / Cocktail").description("Crafted by resident mixologist").category(ServiceCategory.BEVERAGES).unitPrice(14.00).available(true).build(),
                HotelServiceItem.builder().name("Express Laundry (5 Items)").description("Wash, dry, and ironed in 4 hours").category(ServiceCategory.LAUNDRY).unitPrice(30.00).available(true).build(),
                HotelServiceItem.builder().name("Full Body Aromatherapy Massage (60 min)").description("Signature spa treatment").category(ServiceCategory.SPA_WELLNESS).unitPrice(95.00).available(true).build(),
                HotelServiceItem.builder().name("VIP Airport Luxury Transfer").description("Chauffeured Mercedes Sedan").category(ServiceCategory.TRANSPORT).unitPrice(75.00).available(true).build(),
                HotelServiceItem.builder().name("Late Check-out Extension").description("Extension until 4:00 PM").category(ServiceCategory.EXTRA_AMENITIES).unitPrice(40.00).available(true).build()
        );
        serviceRepository.saveAll(services);

        // 3. Seed Sample Active Bookings
        Booking b1 = bookingService.createBooking(BookingRequestDto.builder()
                .guestFullName("Alexander Wright")
                .guestEmail("alexander.wright@techcorp.io")
                .guestPhone("+1 (555) 234-5678")
                .idProofType("Passport")
                .idProofNumber("P98234123")
                .guestAddress("450 Market Street")
                .guestCity("San Francisco")
                .guestCountry("USA")
                .roomId(r201.getId())
                .checkInDate(LocalDate.now().minusDays(2))
                .checkOutDate(LocalDate.now().plusDays(1))
                .numberOfGuests(2)
                .specialRequests("High floor, quiet corner, extra pillows")
                .build());

        Booking b2 = bookingService.createBooking(BookingRequestDto.builder()
                .guestFullName("Sophia Chen")
                .guestEmail("sophia.chen@innovate.co")
                .guestPhone("+1 (555) 876-5432")
                .idProofType("Driver License")
                .idProofNumber("DL-CA-449102")
                .guestAddress("742 Evergreen Terrace")
                .guestCity("Seattle")
                .guestCountry("USA")
                .roomId(r301.getId())
                .checkInDate(LocalDate.now().minusDays(1))
                .checkOutDate(LocalDate.now().plusDays(2))
                .numberOfGuests(3)
                .extraBedCharge(35.00)
                .specialRequests("Honeymoon package, champagne on arrival")
                .build());

        // 4. Generate an Initial Settled Bill for Guest Alexander Wright
        billingService.generateBill(BillRequestDto.builder()
                .bookingId(b1.getId())
                .items(List.of(
                        BillItemDto.builder().itemName("Gourmet Breakfast Buffet").category(ServiceCategory.ROOM_SERVICE).quantity(2).unitPrice(25.00).build(),
                        BillItemDto.builder().itemName("VIP Airport Luxury Transfer").category(ServiceCategory.TRANSPORT).quantity(1).unitPrice(75.00).build(),
                        BillItemDto.builder().itemName("Imported Wine Selection").category(ServiceCategory.BEVERAGES).quantity(1).unitPrice(45.00).build()
                ))
                .discountPercentage(5.0)
                .paymentStatus(PaymentStatus.PAID)
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentTransactionId("TXN-CC-8839201")
                .notes("Settled upon checkout. Loyal member discount 5% applied.")
                .build());
    }
}
