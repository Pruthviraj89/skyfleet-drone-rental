package com.skyfleet.rentals.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.skyfleet.rentals.entity.Role;
import com.skyfleet.rentals.entity.Undertaking;
import com.skyfleet.rentals.entity.User;
import com.skyfleet.rentals.repository.UndertakingRepository;
import com.skyfleet.rentals.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class InitData implements CommandLineRunner {

    
    private UserRepository userRepository;
    
    private UndertakingRepository undertakingRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword("$2a$10$golTy1QhxAvMKCkD/1u/Le0Lp1bBBWXTepZ5zZOJeqp3T3PpM5yMa");
            admin.setPhone("9999999999");
            admin.setRole(Role.ROLE_ADMIN);
            admin.setAddress("Headquarters");
            userRepository.save(admin);
        }
        
        if(undertakingRepository.count()==0) {
        	Undertaking entity= new Undertaking();
        	entity.setDamageClauseText("I agree to pay for any damage caused to the drone during the rental period.");
        	entity.setUniquetext(true);
        	undertakingRepository.save(entity);
        	
        }
    }
}

