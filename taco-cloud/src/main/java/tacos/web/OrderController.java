package tacos.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	
	private OrderRepository orderRepo;
	
	@Autowired
	public OrderController ( OrderRepository orderRepo ) {
		this.orderRepo = orderRepo;
	}
	
	@GetMapping("/current")
	public String orderForm(Model model) {
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder( @Valid  Order order, 
			Errors errors, 
			SessionStatus sessionStatus,
			@AuthenticationPrincipal User user) {

		//check for errors. If so, then go back to the design page.
		if ( errors.hasErrors() ) {
			return "orderForm";
		}
		
		order.setUser(user);

		log.info("Order submitted: " + order + "  by user: " + user.getUsername() +" which has " + order.getTacos().size() + " tacos in it." );
		orderRepo.save(order);			//save the order after submission
		sessionStatus.setComplete();	//clear the order by closing the session.
		
		return "redirect:/";
	}

}
