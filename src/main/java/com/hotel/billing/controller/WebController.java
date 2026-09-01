package com.hotel.billing.controller;

import com.hotel.billing.model.Bill;
import com.hotel.billing.model.RoomType;
import com.hotel.billing.model.ServiceCategory;
import com.hotel.billing.service.BillingService;
import com.hotel.billing.service.BookingService;
import com.hotel.billing.service.DashboardService;
import com.hotel.billing.service.HotelServiceItemService;
import com.hotel.billing.service.RoomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    private final DashboardService dashboardService;
    private final BillingService billingService;
    private final BookingService bookingService;
    private final RoomService roomService;
    private final HotelServiceItemService hotelServiceItemService;

    @Value("${hotel.name:Grand Horizon Luxury Palace & Resort}")
    private String hotelName;

    @Value("${hotel.address:100 Marine Bay Promenade, Mumbai, MH 400001}")
    private String hotelAddress;

    @Value("${hotel.phone:+91 (022) 5550-1999}")
    private String hotelPhone;

    @Value("${hotel.email:billing@grandhorizonresort.in}")
    private String hotelEmail;

    @Value("${hotel.tax-rate-percentage:12.0}")
    private double taxRatePercentage;

    @Value("${hotel.currency-symbol:₹}")
    private String currencySymbol;

    public WebController(DashboardService dashboardService,
                         BillingService billingService,
                         BookingService bookingService,
                         RoomService roomService,
                         HotelServiceItemService hotelServiceItemService) {
        this.dashboardService = dashboardService;
        this.billingService = billingService;
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.hotelServiceItemService = hotelServiceItemService;
    }

    private void populateHotelAttributes(Model model) {
        model.addAttribute("hotelName", hotelName);
        model.addAttribute("hotelAddress", hotelAddress);
        model.addAttribute("hotelPhone", hotelPhone);
        model.addAttribute("hotelEmail", hotelEmail);
        model.addAttribute("defaultTaxRate", taxRatePercentage);
        model.addAttribute("currencySymbol", currencySymbol);
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        populateHotelAttributes(model);
        model.addAttribute("stats", dashboardService.getDashboardStats());
        model.addAttribute("recentBills", billingService.getRecentBills());
        model.addAttribute("activeBookings", bookingService.getActiveBookings());
        return "index";
    }

    @GetMapping("/billing")
    public String billingCenter(Model model) {
        populateHotelAttributes(model);
        model.addAttribute("bills", billingService.getAllBills());
        model.addAttribute("activeBookings", bookingService.getActiveBookings());
        model.addAttribute("availableServices", hotelServiceItemService.getAllAvailableServices());
        model.addAttribute("serviceCategories", ServiceCategory.values());
        return "billing";
    }

    @GetMapping("/invoice/{id}")
    public String viewInvoice(@PathVariable Long id, Model model) {
        populateHotelAttributes(model);
        Bill bill = billingService.getBillById(id);
        model.addAttribute("bill", bill);
        return "invoice";
    }

    @GetMapping("/rooms")
    public String roomManagement(Model model) {
        populateHotelAttributes(model);
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("roomTypes", RoomType.values());
        return "rooms";
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        populateHotelAttributes(model);
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("availableRooms", roomService.getAvailableRooms());
        return "bookings";
    }
}
