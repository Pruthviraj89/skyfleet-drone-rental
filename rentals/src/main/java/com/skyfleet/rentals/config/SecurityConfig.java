package com.skyfleet.rentals.config;

import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig  implements WebMvcConfigurer{

	
	
	private final JWTCustomFilter jwtCustomFilter;
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    	http.csrf(csrf->csrf.disable()).cors(Customizer.withDefaults()).authorizeHttpRequests(auth->
    	auth.requestMatchers("/v*/api-docs/**","/api/undertakings","/api/payments/**","/api/bookings/byCustomerId/{id}","/api/users/me","/swagger-ui/**","/api/drones", "/api/users/auth/login","/api/ratings/**","/api/users/auth/register")
		.permitAll()
		.requestMatchers("/api/drones/add/*").hasAuthority("ROLE_ADMIN")
		.requestMatchers("/api/bookings","/api/ratings/**","/api/drones/getById/*","/api/bookings/all/bookings").hasAuthority("ROLE_USER")
		.anyRequest().authenticated()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    	// form login is enabled by default , to disable it
    			http.formLogin(form -> form.disable());
    	
    	//add custom jwt filter before 1st auth filter 
    			http.addFilterBefore(jwtCustomFilter, 
    					UsernamePasswordAuthenticationFilter.class);
    	return http.build();
    	
    	
    }
    
    
	// configure spring bean - auth mgr
	@Bean
	AuthenticationManager authenticationManager
	(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
    
    
    
}