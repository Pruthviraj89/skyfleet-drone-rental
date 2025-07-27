package com.skyfleet.rentals.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;

import com.skyfleet.rentals.entity.Booking;
import com.skyfleet.rentals.entity.Drone;
import com.skyfleet.rentals.entity.RatingValue;
import com.skyfleet.rentals.entity.User;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatingResponseDTO extends BaseDTO {

	 
	 

	    private Long bookingId;

	 
	    private Long userId;

	   
	    private Long droneId;

	 
	    private RatingValue rating; // Enum for rating values

	    private String comment;
}
