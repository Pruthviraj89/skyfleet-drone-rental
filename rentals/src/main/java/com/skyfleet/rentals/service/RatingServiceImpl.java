package com.skyfleet.rentals.service;

import com.skyfleet.rentals.custom_exceptions.ApiException;

import com.skyfleet.rentals.dto.RatingRequestDTO;
import com.skyfleet.rentals.dto.RatingResponseDTO;
import com.skyfleet.rentals.entity.Booking;
import com.skyfleet.rentals.entity.Drone;
import com.skyfleet.rentals.entity.Rating;
import com.skyfleet.rentals.entity.RatingValue;
import com.skyfleet.rentals.entity.User;
import com.skyfleet.rentals.repository.BookingRepository;
import com.skyfleet.rentals.repository.DroneRepository;
import com.skyfleet.rentals.repository.RatingRepository;
import com.skyfleet.rentals.repository.UserRepository;

import lombok.AllArgsConstructor;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    
    private RatingRepository ratingRepository;
    
   private DroneRepository droneRepository;
   
   private BookingRepository bookingRepository;
   
   private UserRepository userRepository;
   
   
    
    private ModelMapper modelMapper;

    @Override
    public RatingResponseDTO saveRating(RatingRequestDTO rating) {
    	if (rating.getRating() == null) {
            rating.setRating(RatingValue.THREE); // Default rating if not provided
        }
    	
    	Rating entity= modelMapper.map(rating, Rating.class);
    	
    	Drone drone= droneRepository.findById(rating.getDroneId()).orElseThrow(()->new ApiException("Drone_id not found"));
    	User user= userRepository.findById(rating.getUserId()).orElseThrow(()->new ApiException("user_id not found"));
    	Booking booking= bookingRepository.findById(rating.getBookingId()).orElseThrow(()->new ApiException("booking_id not found"));
    	
    	entity.setBooking(booking);
    	entity.setUser(user);
    	entity.setDrone(drone);
    
    	
        ratingRepository.save(entity);
        
        RatingResponseDTO rs=modelMapper.map(entity, RatingResponseDTO.class);
        
        rs.setBookingId(entity.getBooking().getId());
        rs.setUserId(entity.getUser().getId());
       rs.setDroneId(entity.getDrone().getId());

        
        return rs;
    }

    @Override
    public List<RatingResponseDTO> getAllRatings() {
   return   ratingRepository.findAll().stream().map((entity)->{
    	  
    	  
    	 RatingResponseDTO rs=modelMapper.map(entity, RatingResponseDTO.class);
    	 rs.setBookingId(entity.getBooking().getId());
         rs.setUserId(entity.getUser().getId());
        rs.setDroneId(entity.getDrone().getId());
        
        return rs;
      }).toList();
      
    	
    	
    }

    @Override
    public RatingResponseDTO getRatingById(Long id) {
        Rating entity= ratingRepository.findById(id).orElseThrow(()->new ApiException("Rating_id not Found"));
    	
        RatingResponseDTO rs= modelMapper.map(entity, RatingResponseDTO.class);
    	
        		rs.setBookingId(entity.getBooking().getId());
        rs.setUserId(entity.getUser().getId());
       rs.setDroneId(entity.getDrone().getId());
        
      return rs  ;
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
}