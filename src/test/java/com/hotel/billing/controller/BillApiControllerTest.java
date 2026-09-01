package com.hotel.billing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.billing.dto.BillRequestDto;
import com.hotel.billing.model.Bill;
import com.hotel.billing.model.PaymentStatus;
import com.hotel.billing.service.BillingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BillApiController.class)
class BillApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillingService billingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/v1/bills - should return list of bills")
    void testGetAllBills() throws Exception {
        Bill bill = Bill.builder()
                .id(1L)
                .invoiceNumber("INV-2026-0001")
                .netTotal(250.0)
                .paymentStatus(PaymentStatus.PAID)
                .build();

        when(billingService.getAllBills()).thenReturn(List.of(bill));

        mockMvc.perform(get("/api/v1/bills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].invoiceNumber").value("INV-2026-0001"))
                .andExpect(jsonPath("$[0].netTotal").value(250.0));
    }

    @Test
    @DisplayName("POST /api/v1/bills/generate - should create a bill and return 201 Created")
    void testGenerateBill() throws Exception {
        BillRequestDto requestDto = BillRequestDto.builder()
                .bookingId(1L)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        Bill createdBill = Bill.builder()
                .id(2L)
                .invoiceNumber("INV-2026-0002")
                .netTotal(350.0)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        when(billingService.generateBill(any(BillRequestDto.class))).thenReturn(createdBill);

        mockMvc.perform(post("/api/v1/bills/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.invoiceNumber").value("INV-2026-0002"));
    }
}
