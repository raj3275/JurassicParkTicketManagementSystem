package ca.sheridancollege.pate3275.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.pate3275.repositories.TicketRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	@Lazy
	private TicketRepository ticketRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Find user based on their user name
		ca.sheridancollege.pate3275.beans.User user = ticketRepo.findUserAccount(username);
		
		//If the user doesn't exist then throw exception
		if (user == null) {
			System.out.println("User not found.");
			throw new UsernameNotFoundException("User Not Found.");
		}
		
		//Get a list of roles for that user
		List<String> roles = ticketRepo.getRolesById(user.getUserId());
		
		//Change the list of roles into a list of Granted Authorities
		List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			grantList.add(new SimpleGrantedAuthority(role));
		}
		
		//Create a Spring Security User
		//This user import from spring security core
		User springUser = new User(user.getUserName(),
				user.getEncryptedPassword(), grantList);
		
		//Return the Spring Security User as a UserDetails
		
		return (UserDetails)springUser;
	}

}
