package com.skyfleet.rentals.dto;



import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UndertakingRequestDTO extends BaseDTO {

	 
	   private Long bookingId;
	 
	   
	    private Boolean isAccepted;

	    
	    private Double depositAmount;

	    private String damageClauseText;
}
