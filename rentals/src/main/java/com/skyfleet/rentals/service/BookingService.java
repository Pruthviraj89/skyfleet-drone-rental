package com.skyfleet.rentals.service;

import com.skyfleet.rentals.entity.Booking;
import java.util.List;

public interface BookingService {
    Booking saveBooking(Booking booking);
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    void deleteBooking(Long id);
    Booking calculateTotalAmount(Booking booking);
}