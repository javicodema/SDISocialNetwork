package com.uniovi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;;

@Service
public class SecurityService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private UsersRepository userRepo;
	private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}
		return null;
	}

	public void autoLogin(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		UsernamePasswordAuthenticationToken aToken = new UsernamePasswordAuthenticationToken(userDetails, password,
				userDetails.getAuthorities());
		authenticationManager.authenticate(aToken);
		if (aToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(aToken);
			logger.debug(String.format("Auto login %s successfully!", username));
		}
	}

	public boolean loginAdmin(String email, String password) {
		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByUsername(email);
			User user = userRepo.findByEmail(email);
			if(!user.getRole().equals("ROLE_ADMIN")) {
				return false;
			}
		}catch(UsernameNotFoundException e){
			return false;
		}
		UsernamePasswordAuthenticationToken aToken = new UsernamePasswordAuthenticationToken(userDetails, password,
				userDetails.getAuthorities());
		authenticationManager.authenticate(aToken);
		if (aToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(aToken);
			logger.debug(String.format("Admin logged %s successfully!", email));
		}
		return true;
	}
}
