package com.skyfleet.rentals.service;

import com.skyfleet.rentals.dto.AddUserDTO;
import com.skyfleet.rentals.dto.UserLoginDTO;
import com.skyfleet.rentals.dto.UserResponseDTO;
import com.skyfleet.rentals.entity.User;
import java.util.List;

public interface UserService {
   UserResponseDTO saveUser(AddUserDTO user);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    void deleteUser(Long id);
    UserResponseDTO getUserByEmail(UserLoginDTO user);
}