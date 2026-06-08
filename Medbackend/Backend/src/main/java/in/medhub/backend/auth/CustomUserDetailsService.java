package in.medhub.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.medhub.backend.model.User;
import in.medhub.backend.services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user=userService.findByUserName(username);
		
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUserName())
				.password(user.getPassword())
				.build();
	}

}
