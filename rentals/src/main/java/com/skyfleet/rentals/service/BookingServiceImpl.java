package com.skyfleet.rentals.service;

import com.skyfleet.rentals.custom_exceptions.ApiException;
import com.skyfleet.rentals.dto.BookingRequestDTO;
import com.skyfleet.rentals.dto.BookingResponseDTO;
import com.skyfleet.rentals.entity.Booking;

import com.skyfleet.rentals.entity.BookingStatus;
import com.skyfleet.rentals.entity.DeliveryStatus;
import com.skyfleet.rentals.entity.Drone;
import com.skyfleet.rentals.entity.DroneStatus;
import com.skyfleet.rentals.entity.Undertaking;
import com.skyfleet.rentals.entity.User;
import com.skyfleet.rentals.repository.BookingRepository;
import com.skyfleet.rentals.repository.DroneRepository;
import com.skyfleet.rentals.repository.UndertakingRepository;
import com.skyfleet.rentals.repository.UserRepository;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    
    private BookingRepository bookingRepository;
    
    private UserRepository userRepository;
    
    private DroneRepository droneRepository;
    
    private UndertakingRepository undertakingRespository;
    
    private ModelMapper modelMapper;

    @Override
    public BookingResponseDTO saveBooking(BookingRequestDTO booking) {
    	
    	User user =userRepository.findById(booking.getUserId()).orElseThrow(()->new ApiException("No Such User..!"));
    	Drone drone= droneRepository.findById(booking.getDroneId()).orElseThrow(()->new ApiException("No Such Drone Found..!"));
    	
    	List<Undertaking> undertakingList=  undertakingRespository.findDistinctByDamageClauseText();
    
    	List<Undertaking> newUndertaking=new ArrayList<Undertaking>();
    	Booking book=new Booking();
    	
    	
    	if(booking.isUndertakingIsAccepted())
    	{
    		
    		
    		book.setUser(user);
    		book.setDrone(drone);
    		book.setDeliveryDateTime(LocalDateTime.now().plusDays(3));
    		book.setStartTime(booking.getStartTime());
    		book.setEndTime(booking.getEndTime());
    		 calculateTotalAmount(book);
    		book.setStatus(BookingStatus.CONFIRMED);
    		book.setDeliverStatus(DeliveryStatus.PENDING);	
    		
    		undertakingList.forEach(e->{
				Undertaking newEntity=new Undertaking();
				newEntity.setBooking(book);
				newEntity.setDamageClauseText(e.getDamageClauseText());
				newEntity.setDepositAmount(e.getDepositAmount());
				newEntity.setIsAccepted(true);
				newEntity.setUpdatedOn(LocalDateTime.now());
    			newUndertaking.add(newEntity);
    			
    			
    			});
    		bookingRepository.save(book);
    		undertakingRespository.saveAll(newUndertaking);	
    		BookingResponseDTO rs=modelMapper.map(book, BookingResponseDTO.class);
    		
    		
    		rs.setUserId(user.getId());
    		rs.setDroneId(drone.getId());
    		return rs;
    	}else {
    		throw new ApiException("Undertaking Not Accepted..!");
    	} 
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
    	
                 List<Booking> entities =bookingRepository.findAll();
                  
                 
    	
        return entities.stream().map(e->{ 
        	
        	BookingResponseDTO rs= modelMapper.map(e, BookingResponseDTO.class);
        	rs.setUserId(e.getUser().getId());
    		rs.setDroneId(e.getDrone().getId());
    		
        return rs;
        }).toList();
    }

    @Override
    public BookingResponseDTO getBookingById(Long id) {
    	
    	Booking entity= bookingRepository.findById(id).orElseThrow(()-> new ApiException("Booking_id not Found..!"));
    	
       BookingResponseDTO rs=  modelMapper.map(entity, BookingResponseDTO.class) ;
       
       rs.setUserId(entity.getUser().getId());
       rs.setDroneId(entity.getDrone().getId());
       return rs;
    }

    @Override
    public void deleteBooking(Long id) {
    	if(bookingRepository.existsById(id))
    		bookingRepository.deleteById(id);
    	else
    		throw new ApiException("Booking_id not Found ..!");
        
    }

    @Override
    public void calculateTotalAmount(Booking booking) {
        Drone drone = booking.getDrone();
        Duration duration = Duration.between(booking.getStartTime(), booking.getEndTime());
        double total= Math.ceil((duration.toMinutes()/60.0) * drone.getPricePerHour());
        booking.setTotalAmount(total);
        
    }
}