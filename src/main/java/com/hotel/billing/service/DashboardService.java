package com.hotel.billing.service;

import com.hotel.billing.dto.DashboardStatsDto;
import com.hotel.billing.model.BookingStatus;
import com.hotel.billing.model.PaymentStatus;
import com.hotel.billing.model.RoomStatus;
import com.hotel.billing.repository.BillRepository;
import com.hotel.billing.repository.BookingRepository;
import com.hotel.billing.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final BillRepository billRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public DashboardService(BillRepository billRepository,
                            RoomRepository roomRepository,
                            BookingRepository bookingRepository) {
        this.billRepository = billRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public DashboardStatsDto getDashboardStats() {
        Double totalRev = billRepository.calculateTotalPaidRevenue();
        Double pendingRev = billRepository.calculateTotalPendingAmount();

        long totalRooms = roomRepository.count();
        long occupiedRooms = roomRepository.countByStatus(RoomStatus.OCCUPIED);
        long availableRooms = roomRepository.countByStatus(RoomStatus.AVAILABLE);

        double occupancyRate = totalRooms > 0 ? ((double) occupiedRooms / totalRooms) * 100.0 : 0.0;
        occupancyRate = Math.round(occupancyRate * 10.0) / 10.0;

        long activeBookings = bookingRepository.countByStatus(BookingStatus.ACTIVE);
        long paidInvoices = billRepository.countByPaymentStatus(PaymentStatus.PAID);
        long pendingInvoices = billRepository.countByPaymentStatus(PaymentStatus.PENDING);

        return DashboardStatsDto.builder()
                .totalRevenue(totalRev != null ? Math.round(totalRev * 100.0) / 100.0 : 0.0)
                .pendingRevenue(pendingRev != null ? Math.round(pendingRev * 100.0) / 100.0 : 0.0)
                .totalRooms(totalRooms)
                .occupiedRooms(occupiedRooms)
                .availableRooms(availableRooms)
                .occupancyRatePercentage(occupancyRate)
                .activeBookings(activeBookings)
                .totalPaidInvoices(paidInvoices)
                .totalPendingInvoices(pendingInvoices)
                .build();
    }
}
