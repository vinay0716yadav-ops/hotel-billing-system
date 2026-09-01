package com.hotel.billing.repository;

import com.hotel.billing.model.Bill;
import com.hotel.billing.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByInvoiceNumber(String invoiceNumber);
    Optional<Bill> findByBookingId(Long bookingId);
    List<Bill> findByPaymentStatus(PaymentStatus paymentStatus);
    List<Bill> findTop10ByOrderByIssueDateDesc();

    @Query("SELECT SUM(b.netTotal) FROM Bill b WHERE b.paymentStatus = 'PAID'")
    Double calculateTotalPaidRevenue();

    @Query("SELECT SUM(b.netTotal) FROM Bill b WHERE b.paymentStatus = 'PENDING'")
    Double calculateTotalPendingAmount();

    @Query("SELECT SUM(b.netTotal) FROM Bill b WHERE b.paymentStatus = 'PAID' AND b.paidAt >= :since")
    Double calculateRevenueSince(LocalDateTime since);

    long countByPaymentStatus(PaymentStatus paymentStatus);
}
