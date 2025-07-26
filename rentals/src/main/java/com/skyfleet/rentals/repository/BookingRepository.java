package com.skyfleet.rentals.repository;

import com.skyfleet.rentals.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}