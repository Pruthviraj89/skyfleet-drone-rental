package com.skyfleet.rentals.controller;

import com.skyfleet.rentals.dto.AddUserDTO;
import com.skyfleet.rentals.dto.ApiResponse;
import com.skyfleet.rentals.dto.UserLoginDTO;
import com.skyfleet.rentals.dto.UserResponseDTO;
import com.skyfleet.rentals.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    
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

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody AddUserDTO user) {
       // return ResponseEntity.ok(userService.saveUser(user));
    	return ResponseEntity.status(HttpStatus.CREATED)
				.body(userService.saveUser(user));
       
        
    }
    
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> getUserByEmailPassword(@RequestBody UserLoginDTO user) {
      UserResponseDTO entity=userService.getUserByEmail(user);
        return  ResponseEntity.ok(entity) ;
    }
    

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
}