package com.skyfleet.rentals.service;

import com.skyfleet.rentals.entity.Booking;
import com.skyfleet.rentals.entity.BookingStatus;
import com.skyfleet.rentals.entity.Drone;
import com.skyfleet.rentals.entity.DroneStatus;
import com.skyfleet.rentals.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking saveBooking(Booking booking) {
        if (booking.getDrone().getStatus() == DroneStatus.AVAILABLE ) {
        	booking.setStatus(BookingStatus.CONFIRMED);
        	booking.setTotalAmount(calculateTotalAmount(booking).getTotalAmount());
            booking.getDrone().setStatus(DroneStatus.BOOKED);
            return bookingRepository.save(booking);
        }
        return null; // Drone not available
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking calculateTotalAmount(Booking booking) {
        Drone drone = booking.getDrone();
        Duration duration = Duration.between(booking.getStartTime(), booking.getEndTime());
        long hours = duration.toHours();
        double total = hours * drone.getPricePerHour();
        booking.setTotalAmount(total);
        return booking;
    }
}