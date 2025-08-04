package com.skyfleet.rentals.controller;

import com.skyfleet.rentals.dto.AddUserDTO;
import com.skyfleet.rentals.dto.ApiResponse;
import com.skyfleet.rentals.dto.AuthResponseDTO;
import com.skyfleet.rentals.dto.UserLoginDTO;
import com.skyfleet.rentals.dto.UserResponseDTO;
import com.skyfleet.rentals.service.UserService;
import com.skyfleet.rentals.util.JwtUtil;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody AddUserDTO user) {
        UserResponseDTO savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("User registered successfully"));
    }
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO loginDTO) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            // Get user details for token generation
            UserResponseDTO userResponse = userService.authenticateUser(loginDTO);
            
            // Generate JWT token
            String token = jwtUtil.generateToken(userDetails, userResponse.getId(), userResponse.getRole().name());
            
            // Return authentication response
            AuthResponseDTO authResponse = new AuthResponseDTO(token, userResponse);
            return ResponseEntity.ok(authResponse);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Invalid email or password"));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("User Deleted Successfully"));
    }
    
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getCurrentUserProfile() {
        // This will be implemented to get current user from security context
        return ResponseEntity.ok(new ApiResponse("Profile endpoint - to be implemented"));
    }
}