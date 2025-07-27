package com.skyfleet.rentals.controller;

import com.skyfleet.rentals.dto.ApiResponse;
import com.skyfleet.rentals.dto.BookingRequestDTO;

import com.skyfleet.rentals.dto.BookingResponseDTO;
import com.skyfleet.rentals.entity.Booking;
import com.skyfleet.rentals.service.BookingService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO booking) {
    	bookingService.saveBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Booking Created"));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id) {
       return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}