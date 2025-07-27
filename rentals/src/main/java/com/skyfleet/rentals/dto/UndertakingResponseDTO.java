package com.skyfleet.rentals.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skyfleet.rentals.entity.Booking;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UndertakingResponseDTO extends BaseDTO {

	 
	
    private Long id;

    
    private Long bookingId;

    
    private Boolean isAccepted;

    
    private Double depositAmount;

    
    private String damageClauseText;
}
