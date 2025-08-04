package com.skyfleet.rentals.repository;

import com.skyfleet.rentals.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
	
	boolean existsByEmail(String Email);
	
	User findByEmail(String email);
	


}
