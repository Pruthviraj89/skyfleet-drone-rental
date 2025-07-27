package com.skyfleet.rentals.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.skyfleet.rentals.custom_exceptions.ApiException;
import com.skyfleet.rentals.dto.PaymentRequestDTO;
import com.skyfleet.rentals.dto.PaymentResponseDTO;
import com.skyfleet.rentals.dto.RatingResponseDTO;
import com.skyfleet.rentals.entity.Booking;
import com.skyfleet.rentals.entity.Payment;
import com.skyfleet.rentals.entity.PaymentStatus;
import com.skyfleet.rentals.entity.RazorpayOrderResponse;
import com.skyfleet.rentals.repository.BookingRepository;
import com.skyfleet.rentals.repository.PaymentRepository;
import com.skyfleet.rentals.repository.UserRepository;

import lombok.AllArgsConstructor;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {


  

	private PaymentRepository paymentRepository;
    
    private UserRepository userRepository;
    
    private BookingRepository bookingRepository;
    
    private ModelMapper modelMapper;
    
    
    
    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository,
			BookingRepository bookingRepository, ModelMapper modelMapper) {
		super();
		this.paymentRepository = paymentRepository;
		this.userRepository = userRepository;
		this.bookingRepository = bookingRepository;
		this.modelMapper = modelMapper;
	}
    
    
    
    
    
 
    

    @Override
    public PaymentResponseDTO savePayment(PaymentRequestDTO payment){
//    	if (payment.getPaymentStatus() == null) {
//            payment.setPaymentStatus(PaymentStatus.PENDING);
//        }
//        return paymentRepository.save(payment);
    	
    	
    	
    	if(bookingRepository.existsById(payment.getBookingId())) {
    		try {
    			RazorpayClient razorpayClient= new RazorpayClient(apiKey,apiSecret);
        		
        		JSONObject 	orderRequest= new JSONObject();
        		
        		orderRequest.put("amount", payment.getAmountPaid()*100);
        		orderRequest.put("currency", "INR");
        		orderRequest.put("receipt", payment.getBookingId().toString());
        		
        		Order order=razorpayClient.orders.create(orderRequest);
        		
        		
        		
        	
        		ObjectMapper objectMapper = new ObjectMapper();
        		
        		RazorpayOrderResponse razorpayOrder = objectMapper.readValue(order.toString(), RazorpayOrderResponse.class);
        		
        		//System.out.println(razorpayOrder.getId());
        		
        		
        		
        		Payment entity= new Payment();
        		
        		Booking booking= bookingRepository.getReferenceById(payment.getBookingId());
        		
        		
        		entity.setBooking(booking);
        		entity.setAmountPaid(payment.getAmountPaid());
        		entity.setPaymentStatus(PaymentStatus.PENDING);
        		entity.setRazorpayPaymentId(razorpayOrder.getReceipt());
        		entity.setRazorpayOrderId(razorpayOrder.getId());
        		entity.setPaymentMethod("Not Known");
        		
        		paymentRepository.save(entity);
        		
        		
        		
        		
    		}catch (Exception e) {
				// TODO: handle exception
    			throw new ApiException(e.getMessage());
			}
    	}else {
    		throw new ApiException("booking_id Not Found");
    	}
    	
    	return null;
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
       // return paymentRepository.findAll();
    	
    return	paymentRepository.findAll().stream().map((entity)->{
      	  
      	  
       	 PaymentResponseDTO rs=modelMapper.map(entity, PaymentResponseDTO.class);
       	 rs.setBookingId(entity.getBooking().getId());
           return rs;
         }).toList();
    	
    	
    	
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long id) {
       // return paymentRepository.findById(id).orElse(null);
    	
    	Payment entity=paymentRepository.findById(id).orElseThrow(()->new ApiException("Payment_id not Found"));
    	
    	PaymentResponseDTO rs= modelMapper.map(entity, PaymentResponseDTO.class);
    	
		rs.setBookingId(entity.getBooking().getId());

   	
        return rs;
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
       
    }
}