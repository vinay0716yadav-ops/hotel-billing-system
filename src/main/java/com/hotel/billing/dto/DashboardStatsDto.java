package com.hotel.billing.dto;

public class DashboardStatsDto {
    private double totalRevenue;
    private double pendingRevenue;
    private long totalRooms;
    private long occupiedRooms;
    private long availableRooms;
    private double occupancyRatePercentage;
    private long activeBookings;
    private long totalPaidInvoices;
    private long totalPendingInvoices;

    public DashboardStatsDto() {}

    public DashboardStatsDto(double totalRevenue, double pendingRevenue, long totalRooms, long occupiedRooms, long availableRooms, double occupancyRatePercentage, long activeBookings, long totalPaidInvoices, long totalPendingInvoices) {
        this.totalRevenue = totalRevenue;
        this.pendingRevenue = pendingRevenue;
        this.totalRooms = totalRooms;
        this.occupiedRooms = occupiedRooms;
        this.availableRooms = availableRooms;
        this.occupancyRatePercentage = occupancyRatePercentage;
        this.activeBookings = activeBookings;
        this.totalPaidInvoices = totalPaidInvoices;
        this.totalPendingInvoices = totalPendingInvoices;
    }

    public static DashboardStatsDtoBuilder builder() {
        return new DashboardStatsDtoBuilder();
    }

    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
    public double getPendingRevenue() { return pendingRevenue; }
    public void setPendingRevenue(double pendingRevenue) { this.pendingRevenue = pendingRevenue; }
    public long getTotalRooms() { return totalRooms; }
    public void setTotalRooms(long totalRooms) { this.totalRooms = totalRooms; }
    public long getOccupiedRooms() { return occupiedRooms; }
    public void setOccupiedRooms(long occupiedRooms) { this.occupiedRooms = occupiedRooms; }
    public long getAvailableRooms() { return availableRooms; }
    public void setAvailableRooms(long availableRooms) { this.availableRooms = availableRooms; }
    public double getOccupancyRatePercentage() { return occupancyRatePercentage; }
    public void setOccupancyRatePercentage(double occupancyRatePercentage) { this.occupancyRatePercentage = occupancyRatePercentage; }
    public long getActiveBookings() { return activeBookings; }
    public void setActiveBookings(long activeBookings) { this.activeBookings = activeBookings; }
    public long getTotalPaidInvoices() { return totalPaidInvoices; }
    public void setTotalPaidInvoices(long totalPaidInvoices) { this.totalPaidInvoices = totalPaidInvoices; }
    public long getTotalPendingInvoices() { return totalPendingInvoices; }
    public void setTotalPendingInvoices(long totalPendingInvoices) { this.totalPendingInvoices = totalPendingInvoices; }

    public static class DashboardStatsDtoBuilder {
        private double totalRevenue;
        private double pendingRevenue;
        private long totalRooms;
        private long occupiedRooms;
        private long availableRooms;
        private double occupancyRatePercentage;
        private long activeBookings;
        private long totalPaidInvoices;
        private long totalPendingInvoices;

        public DashboardStatsDtoBuilder totalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; return this; }
        public DashboardStatsDtoBuilder pendingRevenue(double pendingRevenue) { this.pendingRevenue = pendingRevenue; return this; }
        public DashboardStatsDtoBuilder totalRooms(long totalRooms) { this.totalRooms = totalRooms; return this; }
        public DashboardStatsDtoBuilder occupiedRooms(long occupiedRooms) { this.occupiedRooms = occupiedRooms; return this; }
        public DashboardStatsDtoBuilder availableRooms(long availableRooms) { this.availableRooms = availableRooms; return this; }
        public DashboardStatsDtoBuilder occupancyRatePercentage(double occupancyRatePercentage) { this.occupancyRatePercentage = occupancyRatePercentage; return this; }
        public DashboardStatsDtoBuilder activeBookings(long activeBookings) { this.activeBookings = activeBookings; return this; }
        public DashboardStatsDtoBuilder totalPaidInvoices(long totalPaidInvoices) { this.totalPaidInvoices = totalPaidInvoices; return this; }
        public DashboardStatsDtoBuilder totalPendingInvoices(long totalPendingInvoices) { this.totalPendingInvoices = totalPendingInvoices; return this; }
        public DashboardStatsDto build() {
            return new DashboardStatsDto(totalRevenue, pendingRevenue, totalRooms, occupiedRooms, availableRooms, occupancyRatePercentage, activeBookings, totalPaidInvoices, totalPendingInvoices);
        }
    }
}
