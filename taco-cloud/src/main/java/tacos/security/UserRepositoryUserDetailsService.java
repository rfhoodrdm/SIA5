package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tacos.User;
import tacos.data.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
	
	/** *************************************************
	 * 			Member Variables and Constants
	 *  ************************************************* */ 


	private final UserRepository userRepo;
	
	 /** *************************************************
	  * 					Bean Definitions
	  *  ************************************************* */ 
	 
	 /** *************************************************
	  * 					Initialization
	  *  ************************************************* */ 

	@Autowired
	public UserRepositoryUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	

	/** *************************************************
	 * 						API
	 *  ************************************************* */ 
	
	/**
	 * Must never return null.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findUserByUsername(username);
		
		if ( null != user ) {
			return user;
		} else {
			throw new UsernameNotFoundException("User '" + username + "' not found.");
		}
	}



}
