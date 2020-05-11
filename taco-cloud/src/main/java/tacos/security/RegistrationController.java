package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.User;
import tacos.data.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	
	/**
	 * Constructor uses bean definitions from @Bean decorated methods in other classes for the password Encoder.
	 * @param userRepository
	 * @param passwordEncoder
	 */
	@Autowired
	public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepository;
		this.passwordEncoder = passwordEncoder;
	}



	@GetMapping
	public String registerForm() {
		return "registration";
	}
	
	@PostMapping
	public String processRegistration (RegistrationForm form) {
		User newUser = form.toUser(passwordEncoder);
		userRepo.save(newUser);
		return "redirect:/login";
	}
	
}
