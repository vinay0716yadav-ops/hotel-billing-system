package com.hotel.billing.service;

import com.hotel.billing.dto.BillItemDto;
import com.hotel.billing.dto.BillRequestDto;
import com.hotel.billing.dto.PaymentRequestDto;
import com.hotel.billing.model.*;
import com.hotel.billing.repository.BillRepository;
import com.hotel.billing.repository.BookingRepository;
import com.hotel.billing.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    private BillingService billingService;

    private Booking sampleBooking;
    private Room sampleRoom;
    private Guest sampleGuest;

    @BeforeEach
    void setUp() {
        billingService = new BillingService(billRepository, bookingRepository, roomRepository, 12.0);

        sampleRoom = Room.builder()
                .id(1L)
                .roomNumber("201")
                .roomType(RoomType.DELUXE)
                .pricePerNight(150.00)
                .status(RoomStatus.OCCUPIED)
                .build();

        sampleGuest = Guest.builder()
                .id(1L)
                .fullName("John Doe")
                .email("john@example.com")
                .phone("+15551234567")
                .build();

        sampleBooking = Booking.builder()
                .id(1L)
                .bookingReference("BK-202609-1001")
                .guest(sampleGuest)
                .room(sampleRoom)
                .checkInDate(LocalDate.now().minusDays(2))
                .checkOutDate(LocalDate.now())
                .status(BookingStatus.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("Should correctly calculate room charges, services, 12% tax, 10% discount, and net total")
    void testGenerateBillCalculation() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(sampleBooking));
        when(billRepository.findByBookingId(1L)).thenReturn(Optional.empty());
        when(billRepository.save(any(Bill.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BillRequestDto dto = BillRequestDto.builder()
                .bookingId(1L)
                .items(List.of(
                        BillItemDto.builder().itemName("Breakfast Buffet").unitPrice(20.0).quantity(2).category(ServiceCategory.ROOM_SERVICE).build(),
                        BillItemDto.builder().itemName("Airport Taxi").unitPrice(60.0).quantity(1).category(ServiceCategory.TRANSPORT).build()
                ))
                .customTaxPercentage(12.0)
                .discountPercentage(10.0)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        Bill bill = billingService.generateBill(dto);

        // Stay nights = 2, room rate = $150 -> Room charge = $300
        assertThat(bill.getStayNights()).isEqualTo(2);
        assertThat(bill.getTotalRoomCharge()).isEqualTo(300.0);

        // Services = 2*20 + 1*60 = $100
        assertThat(bill.getTotalServicesCharge()).isEqualTo(100.0);

        // Subtotal = 300 + 100 = $400
        assertThat(bill.getSubtotal()).isEqualTo(400.0);

        // 12% Tax on 400 = $48
        assertThat(bill.getTaxAmount()).isEqualTo(48.0);

        // 10% Discount on 400 = $40
        assertThat(bill.getDiscountAmount()).isEqualTo(40.0);

        // Net Total = 400 + 48 - 40 = $408
        assertThat(bill.getNetTotal()).isEqualTo(408.0);
        assertThat(bill.getPaymentStatus()).isEqualTo(PaymentStatus.PENDING);
        assertThat(bill.getInvoiceNumber()).startsWith("INV-");
    }

    @Test
    @DisplayName("Should process payment and update status to PAID")
    void testProcessPayment() {
        Bill existingBill = Bill.builder()
                .id(10L)
                .invoiceNumber("INV-2026-1001")
                .booking(sampleBooking)
                .netTotal(408.0)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        when(billRepository.findById(10L)).thenReturn(Optional.of(existingBill));
        when(billRepository.save(any(Bill.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PaymentRequestDto payDto = PaymentRequestDto.builder()
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .transactionId("TXN-TEST-12345")
                .notes("Settled via Visa")
                .build();

        Bill updated = billingService.processPayment(10L, payDto);

        assertThat(updated.getPaymentStatus()).isEqualTo(PaymentStatus.PAID);
        assertThat(updated.getPaymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
        assertThat(updated.getPaymentTransactionId()).isEqualTo("TXN-TEST-12345");
        assertThat(updated.getPaidAt()).isNotNull();
        assertThat(sampleBooking.getStatus()).isEqualTo(BookingStatus.CHECKED_OUT);
        assertThat(sampleRoom.getStatus()).isEqualTo(RoomStatus.AVAILABLE);
    }
}
