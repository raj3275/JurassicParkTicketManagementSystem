package ca.sheridancollege.pate3275.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig {

	//Authorization
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		//////////////////////////////////////////
		//Remove this code before production
		http.csrf().disable();
		http.headers().frameOptions().disable();
		//////////////////////////////////////////
		
		
		http
		.authorizeHttpRequests((authz) -> authz
				//identify the Mapping and the roles that can access it
				//.antMatchers(HTTP Method, URL).hasRole(specific Role)
				//.antMatchers(HTTP Method, URL).hasAnyRole(all Role)
				.antMatchers(HttpMethod.GET, "/add").hasAnyRole("VENDOR")
				.antMatchers(HttpMethod.GET, "/edit/{id}").hasAnyRole("VENDOR")
				.antMatchers(HttpMethod.GET, "/delete/{id}").hasAnyRole("VENDOR")
				.antMatchers(HttpMethod.GET, "/view").hasAnyRole("VENDOR", "GUEST")
				.antMatchers(HttpMethod.GET, "/viewUsers").hasRole("VENDOR")
				//Pages that we don't require a login
				//.antMatchers(HTTP Method, URL).permitAll()
				.antMatchers(HttpMethod.GET, "/").permitAll()
				.antMatchers(HttpMethod.GET, "/register").permitAll()
				.antMatchers(HttpMethod.POST, "/register").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/css/**", "/images/**").permitAll()
				.anyRequest().authenticated()
				)
		.formLogin()
				.loginPage("/login")//Mapping in the controller
				.permitAll()
			.and()
				.logout()
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/?logout")
			.and()
				.exceptionHandling()
					.accessDeniedHandler(accessDeniedHandler);
		return http.build();
	}
	
	@Autowired
	LoginAccessDeniedHandler accessDeniedHandler;
	
	//Authentication
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http)
	throws Exception {
	return http.getSharedObject(AuthenticationManagerBuilder.class)
	.userDetailsService(userDetailsService)
	.passwordEncoder(encoder)
	.and()
	.build();
	}
	
}
