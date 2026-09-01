package com.hotel.billing.service;

import com.hotel.billing.dto.BillItemDto;
import com.hotel.billing.dto.BillRequestDto;
import com.hotel.billing.dto.PaymentRequestDto;
import com.hotel.billing.model.*;
import com.hotel.billing.repository.BillRepository;
import com.hotel.billing.repository.BookingRepository;
import com.hotel.billing.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class BillingService {

    private final BillRepository billRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final double defaultTaxPercentage;
    private static final AtomicInteger sequence = new AtomicInteger(1001);

    public BillingService(
            BillRepository billRepository,
            BookingRepository bookingRepository,
            RoomRepository roomRepository,
            @Value("${hotel.tax-rate-percentage:12.0}") double defaultTaxPercentage) {
        this.billRepository = billRepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.defaultTaxPercentage = defaultTaxPercentage;
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> getRecentBills() {
        return billRepository.findTop10ByOrderByIssueDateDesc();
    }

    public Bill getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Bill not found with ID: " + id));
    }

    public Bill getBillByInvoiceNumber(String invoiceNumber) {
        return billRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new NoSuchElementException("Bill not found with Invoice Number: " + invoiceNumber));
    }

    public Bill getBillByBookingId(Long bookingId) {
        return billRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new NoSuchElementException("No bill found for Booking ID: " + bookingId));
    }

    public Bill generateBill(BillRequestDto dto) {
        Booking booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new NoSuchElementException("Booking not found with ID: " + dto.getBookingId()));

        // Check if a bill already exists for this booking
        Bill bill = billRepository.findByBookingId(booking.getId())
                .orElseGet(() -> Bill.builder()
                        .booking(booking)
                        .invoiceNumber(generateInvoiceNumber())
                        .build());

        // Stay calculations
        long stayNights = booking.getStayDurationNights();
        double ratePerNight = booking.getEffectiveRoomRate();
        double roomTotal = Math.round(stayNights * ratePerNight * 100.0) / 100.0;
        double extraBedTotal = Math.round(dto.getExtraBedCharges() * stayNights * 100.0) / 100.0;

        bill.setStayNights(stayNights);
        bill.setRoomRatePerNight(ratePerNight);
        bill.setTotalRoomCharge(roomTotal + extraBedTotal);

        // Clear existing items and re-populate
        bill.getItems().clear();
        double servicesTotal = 0.0;

        if (dto.getItems() != null) {
            for (BillItemDto itemDto : dto.getItems()) {
                double itemTotalPrice = Math.round(itemDto.getQuantity() * itemDto.getUnitPrice() * 100.0) / 100.0;
                BillItem item = BillItem.builder()
                        .itemName(itemDto.getItemName())
                        .category(itemDto.getCategory() != null ? itemDto.getCategory() : ServiceCategory.ROOM_SERVICE)
                        .quantity(itemDto.getQuantity())
                        .unitPrice(itemDto.getUnitPrice())
                        .totalPrice(itemTotalPrice)
                        .serviceDate(LocalDateTime.now())
                        .build();
                bill.addItem(item);
                servicesTotal += itemTotalPrice;
            }
        }

        servicesTotal = Math.round(servicesTotal * 100.0) / 100.0;
        bill.setTotalServicesCharge(servicesTotal);

        // Subtotal
        double subtotal = Math.round((bill.getTotalRoomCharge() + servicesTotal) * 100.0) / 100.0;
        bill.setSubtotal(subtotal);

        // Tax
        double taxPct = dto.getCustomTaxPercentage() != null ? dto.getCustomTaxPercentage() : defaultTaxPercentage;
        bill.setTaxPercentage(taxPct);
        double taxAmount = Math.round((subtotal * (taxPct / 100.0)) * 100.0) / 100.0;
        bill.setTaxAmount(taxAmount);

        // Discount
        double discountPct = dto.getDiscountPercentage();
        bill.setDiscountPercentage(discountPct);
        double discountAmount = Math.round((subtotal * (discountPct / 100.0)) * 100.0) / 100.0;
        bill.setDiscountAmount(discountAmount);

        // Net Total
        double netTotal = Math.round((subtotal + taxAmount - discountAmount) * 100.0) / 100.0;
        bill.setNetTotal(netTotal);

        // Payment status & methods
        PaymentStatus status = dto.getPaymentStatus() != null ? dto.getPaymentStatus() : PaymentStatus.PENDING;
        bill.setPaymentStatus(status);
        bill.setPaymentMethod(dto.getPaymentMethod());
        bill.setPaymentTransactionId(dto.getPaymentTransactionId());
        bill.setNotes(dto.getNotes());

        if (status == PaymentStatus.PAID) {
            bill.setPaidAt(LocalDateTime.now());
            // Update booking status and room status
            booking.setStatus(BookingStatus.CHECKED_OUT);
            Room room = booking.getRoom();
            if (room != null) {
                room.setStatus(RoomStatus.AVAILABLE);
                roomRepository.save(room);
            }
            bookingRepository.save(booking);
        }

        return billRepository.save(bill);
    }

    public Bill processPayment(Long billId, PaymentRequestDto dto) {
        Bill bill = getBillById(billId);
        bill.setPaymentStatus(PaymentStatus.PAID);
        bill.setPaymentMethod(dto.getPaymentMethod());
        bill.setPaymentTransactionId(dto.getTransactionId());
        bill.setPaidAt(LocalDateTime.now());
        if (dto.getNotes() != null && !dto.getNotes().isBlank()) {
            bill.setNotes(bill.getNotes() != null ? bill.getNotes() + " | " + dto.getNotes() : dto.getNotes());
        }

        // Complete checkout on payment
        Booking booking = bill.getBooking();
        if (booking != null) {
            booking.setStatus(BookingStatus.CHECKED_OUT);
            Room room = booking.getRoom();
            if (room != null) {
                room.setStatus(RoomStatus.AVAILABLE);
                roomRepository.save(room);
            }
            bookingRepository.save(booking);
        }

        return billRepository.save(bill);
    }

    private String generateInvoiceNumber() {
        String datePrefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return String.format("INV-%s-%04d", datePrefix, sequence.getAndIncrement());
    }
}
