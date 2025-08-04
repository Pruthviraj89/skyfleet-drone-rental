package com.skyfleet.rentals.config;

import com.skyfleet.rentals.entity.Role;
import com.skyfleet.rentals.entity.User;
import com.skyfleet.rentals.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if it doesn't exist
        if (!userRepository.existsByEmail("admin@skyfleet.com")) {
            User adminUser = new User();
            adminUser.setName("Admin User");
            adminUser.setEmail("admin@skyfleet.com");
            adminUser.setPassword(passwordEncoder.encode("Admin123"));
            adminUser.setPhone("1234567890");
            adminUser.setAddress("Admin Address");
            adminUser.setRole(Role.ADMIN);
            userRepository.save(adminUser);
            System.out.println("Admin user created: admin@skyfleet.com / admin123");
        }

        // Create test user if it doesn't exist
        if (!userRepository.existsByEmail("user@test.com")) {
            User testUser = new User();
            testUser.setName("Test User");
            testUser.setEmail("user@test.com");
            testUser.setPassword(passwordEncoder.encode("user123"));
            testUser.setPhone("9876543210");
            testUser.setAddress("Test Address");
            testUser.setRole(Role.USER);
            userRepository.save(testUser);
            System.out.println("Test user created: user@test.com / user123");
        }
    }
} 