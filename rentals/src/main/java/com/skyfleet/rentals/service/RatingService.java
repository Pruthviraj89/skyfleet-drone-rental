package com.skyfleet.rentals.service;

import com.skyfleet.rentals.entity.Rating;
import java.util.List;

public interface RatingService {
    Rating saveRating(Rating rating);
    List<Rating> getAllRatings();
    Rating getRatingById(Long id);
    void deleteRating(Long id);
}