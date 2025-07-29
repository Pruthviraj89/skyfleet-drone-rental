package com.skyfleet.rentals.controller;


import com.skyfleet.rentals.config.JwtUtils;
import com.skyfleet.rentals.dto.AddUserDTO;
import com.skyfleet.rentals.dto.ApiResponse;
import com.skyfleet.rentals.dto.AuthResponse;
import com.skyfleet.rentals.dto.UserLoginDTO;
import com.skyfleet.rentals.dto.UserResponseDTO;
import com.skyfleet.rentals.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
	
	private final AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtUtils jwtUtils;
    
    /*
	 *
	 * Desc -Add new food Item to the Restaurant
	 * URL - http://host:port/food_items/restaurants/{restaurantId}
	 * Method - POST
	 * Payload - req body - food item details (FoodItemDTO)
	 * Resp -  success - SC 201 , ApiResp - success mesg
	 *   Error - invalid restaurant id | dup food item ... ApiResp - err mesg
	 * 
	 */

    @PostMapping("/auth/register")
    public ResponseEntity<?> createUser(@RequestBody AddUserDTO user) {
       // return ResponseEntity.ok(userService.saveUser(user));
    	return ResponseEntity.status(HttpStatus.CREATED)
				.body(userService.saveUser(user));
       
        
    }
    
    
//    @PostMapping("/auth/login")
//    public ResponseEntity<?> getUserByEmailPassword(@RequestBody UserLoginDTO user) {
//      UserResponseDTO entity=userService.getUserByEmail(user);
//        return  ResponseEntity.ok(entity) ;
//    }
    

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return  ResponseEntity.ok(user) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("User Deleted Successfully"));
    }
    @PostMapping("/auth/login")
	public ResponseEntity<?> userSignIn(@RequestBody  UserLoginDTO dto) {
		System.out.println("in user sign in " + dto);
		// 1. create Authentication token (UsernamePasswordAuthToken - username(em) ,
		// pwd
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(),
				dto.getPassword());
		System.out.println("is authenticated " + authToken.isAuthenticated());// f
		// 2. invoke AuthenticationManager's - authenticate method - spring sec supplied
		Authentication successfulAuth = authenticationManager.authenticate(authToken);
		// in case of failure - throws AuthenticationException
		// in case of success- rets user details object - within auth
		// 3. => success
		System.out.println("is authenticated " + successfulAuth.isAuthenticated());// true
		System.out.println("principal " + successfulAuth.getPrincipal());// user details + granted authorities
		System.out.println("principal class" + successfulAuth.getPrincipal().getClass());// com.app.entities.UserEntity
																							// - UserDetails
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new AuthResponse("successful login ....", 
						jwtUtils.generateJwtToken(successfulAuth)
						));
	}
    
    
    
}