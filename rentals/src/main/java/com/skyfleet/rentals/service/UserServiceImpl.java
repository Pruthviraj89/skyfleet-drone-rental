package com.skyfleet.rentals.service;

import com.skyfleet.rentals.custom_exceptions.ApiException;

import com.skyfleet.rentals.dto.AddUserDTO;
import com.skyfleet.rentals.dto.UserLoginDTO;
import com.skyfleet.rentals.dto.UserResponseDTO;
import com.skyfleet.rentals.entity.Role;
import com.skyfleet.rentals.entity.User;
import com.skyfleet.rentals.repository.UserRepository;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    
    private UserRepository userRepository;
    
    
    private ModelMapper modelMapper;
    
    
    private PasswordEncoder encoder;
    

    @Override
    public UserResponseDTO saveUser(AddUserDTO user) {
       if(userRepository.existsByEmail(user.getEmail()))
    	   throw new ApiException("User Already Exists....!!!!");
       User entity= modelMapper.map(user, User.class);
       
       entity.setRole(Role.ROLE_USER);
       entity.setPassword(encoder.encode(entity.getPassword()));
        
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
		// TODO Auto-generated method stub
		User Entity= userRepository.findByEmail(user.getEmail());
		
		if(Entity!=null)
			return modelMapper.map(Entity, UserResponseDTO.class);
		else
			throw new ApiException("User Not Found");
	}

	@Override
	public UserResponseDTO getUserByEmailAfterTokenVerification(String email) {
		// TODO Auto-generated method stub
				User Entity= userRepository.findByEmail(email);
				
				if(Entity!=null)
					return modelMapper.map(Entity, UserResponseDTO.class);
				else
					throw new ApiException("User Not Found");
	}

	@Override
	public UserResponseDTO updateUser(User user) {
		  if(userRepository.existsByEmail(user.getEmail()))
			  userRepository.save(user);
	        
	        return modelMapper.map(user, UserResponseDTO.class);

	}
	
	
	
	
}