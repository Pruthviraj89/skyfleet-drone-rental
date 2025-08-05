package com.skyfleet.rentals.service;

import com.skyfleet.rentals.custom_exceptions.ApiException;

import com.skyfleet.rentals.dto.AddUserDTO;
import com.skyfleet.rentals.dto.UserLoginDTO;
import com.skyfleet.rentals.dto.UserResponseDTO;
import com.skyfleet.rentals.entity.Role;
import com.skyfleet.rentals.entity.User;
import com.skyfleet.rentals.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    
    private UserRepository userRepository;
    
    
    private ModelMapper modelMapper;
    
    
    private PasswordEncoder passwordEncoder;
    

    @Override
    public UserResponseDTO saveUser(AddUserDTO user) {
       if(userRepository.existsByEmail(user.getEmail()))
    	   throw new ApiException("User Already Exists....!!!!");
       User entity= modelMapper.map(user, User.class);
       
       // Encode password before saving
       entity.setPassword(passwordEncoder.encode(user.getPassword()));
       entity.setRole(Role.USER);
        
        User persistEntity=userRepository.save(entity);
        
        return modelMapper.map(persistEntity, UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {	
    	return userRepository.findAll().stream().map(entity-> modelMapper.map(entity, UserResponseDTO.class)).toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ApiException("User Not Exists"));

        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
    	if(!userRepository.findById(id).isPresent())
    		throw new ApiException("User Not Exists");
    	else
    		userRepository.deleteById(id);
       
    }

	@Override
	public UserResponseDTO getUserByEmail(UserLoginDTO user) {
		User Entity= userRepository.findByEmail(user.getEmail());
		
		if(Entity!=null)
			return modelMapper.map(Entity, UserResponseDTO.class);
		else
			throw new ApiException("User Not Found");
	}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

    public UserResponseDTO authenticateUser(UserLoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if (user == null) {
            throw new ApiException("User not found");
        }
        
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new ApiException("Invalid password");
        }
        
        return modelMapper.map(user, UserResponseDTO.class);
    }
    
    
    
    @Override
	public UserResponseDTO getUserByEmailAfterTokenVerification(String email) {
		// TODO Auto-generated method stub
				User Entity= userRepository.findByEmail(email);
				
				if(Entity!=null)
					return modelMapper.map(Entity, UserResponseDTO.class);
				else
					throw new ApiException("Token Auth failed");
	}

	@Override
	public UserResponseDTO updateUser(User user) {
		  if(userRepository.existsByEmail(user.getEmail()))
			  userRepository.save(user);
	        
	        return modelMapper.map(user, UserResponseDTO.class);

	}

	
}