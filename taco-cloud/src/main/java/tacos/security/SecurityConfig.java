package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	//in-memory based.
//	@Override
//	public void configure( AuthenticationManagerBuilder auth ) throws Exception {
//		//add user buzz.
//		auth.inMemoryAuthentication()
//		.withUser("buzz").password("{noop}infinity").authorities("ROLE_USER");
//		
//		//add user woody.
//		auth.inMemoryAuthentication()
//		.withUser("woody").password("{noop}bullseye").authorities("ROLE_USER");
//		
//	}
	
	// JDBC-based.
//	@Autowired
//	DataSource dataSource;
//	
//	@Override
//	public void configure( AuthenticationManagerBuilder auth ) throws Exception {
//		
//		auth.jdbcAuthentication().dataSource(dataSource);
//			
//	}
	
	
	//Custom user store.
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		//return new StandardPasswordEncoder("53cr3t");		//deprecated, but following code example from book.
	}
	
	@Override
	public void configure( AuthenticationManagerBuilder auth ) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	
	@Override
	protected void configure ( HttpSecurity http ) throws Exception {
		http.authorizeRequests()
		.antMatchers("/design", "/orders").access("hasRole('ROLE_USER')")
		.antMatchers("/", "/**").permitAll()
			.and()
		.formLogin().loginPage("/login")
			.and()
		.logout().logoutSuccessUrl("/");
			//.and()
		//.csrf().disable();	//disable csrf for now, until we get to that part of the chapter, so we can do post requests.
	}
	
}
