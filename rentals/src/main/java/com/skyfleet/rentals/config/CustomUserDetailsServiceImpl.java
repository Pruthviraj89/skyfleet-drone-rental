package com.skyfleet.rentals.config;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skyfleet.rentals.entity.User;
import com.skyfleet.rentals.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	//depcy  - user dao
	private final UserRepository userDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
	
		if(userDao.existsByEmail(email)) {
			User user=userDao.findByEmail(email);
			
			return user;
		}else {
			throw new UsernameNotFoundException("Invalid email .....");
		}
			
			

		
	}

}
