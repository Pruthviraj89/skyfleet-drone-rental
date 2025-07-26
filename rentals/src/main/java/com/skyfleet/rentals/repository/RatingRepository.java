package com.skyfleet.rentals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyfleet.rentals.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{

}
