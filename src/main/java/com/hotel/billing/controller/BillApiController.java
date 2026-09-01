package com.hotel.billing.controller;

import com.hotel.billing.dto.BillRequestDto;
import com.hotel.billing.dto.PaymentRequestDto;
import com.hotel.billing.model.Bill;
import com.hotel.billing.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
@CrossOrigin(origins = "*")
public class BillApiController {

    private final BillingService billingService;

    public BillApiController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billingService.getAllBills());
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Bill>> getRecentBills() {
        return ResponseEntity.ok(billingService.getRecentBills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.getBillById(id));
    }

    @GetMapping("/invoice/{invoiceNumber}")
    public ResponseEntity<Bill> getBillByInvoiceNumber(@PathVariable String invoiceNumber) {
        return ResponseEntity.ok(billingService.getBillByInvoiceNumber(invoiceNumber));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Bill> getBillByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(billingService.getBillByBookingId(bookingId));
    }

    @PostMapping("/generate")
    public ResponseEntity<Bill> generateBill(@Valid @RequestBody BillRequestDto dto) {
        Bill createdBill = billingService.generateBill(dto);
        return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Bill> processPayment(@PathVariable Long id, @Valid @RequestBody PaymentRequestDto dto) {
        Bill updatedBill = billingService.processPayment(id, dto);
        return ResponseEntity.ok(updatedBill);
    }
}
