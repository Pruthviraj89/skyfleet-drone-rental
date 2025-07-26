package com.skyfleet.rentals.service;

import com.skyfleet.rentals.entity.Rating;
import com.skyfleet.rentals.entity.RatingValue;
import com.skyfleet.rentals.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating saveRating(Rating rating) {
    	if (rating.getRating() == null) {
            rating.setRating(RatingValue.THREE); // Default rating if not provided
        }
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating getRatingById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
}