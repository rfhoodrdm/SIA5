package tacos.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@ConfigurationProperties(prefix="taco.orders")
public class OrderController {
	
	/** *************************************************
	 * 			Member Variables and Constants
	 *  ************************************************* */ 
	
	private OrderRepository orderRepo;
	private OrderProps orderProps;
	
	
	/** *************************************************
	 * 					Bean Definitions
	 *  ************************************************* */ 
	
	/** *************************************************
	 * 					Initialization
	 *  ************************************************* */ 
	
	@Autowired
	public OrderController(OrderRepository orderRepo, OrderProps orderProps) {
		super();
		this.orderRepo = orderRepo;
		this.orderProps = orderProps;
	}
	
	
	/** *************************************************
	 * 						API
	 *  ************************************************* */ 	


	@GetMapping("/current")
	public String orderForm(Model model) {
		return "orderForm";
	}
	
	
	@GetMapping()
	public String ordersForUsers( @AuthenticationPrincipal User user,  Model model ) {
		
		Pageable pageable = PageRequest.of(0, orderProps.getPageSize() );
		model.addAttribute("orders", orderRepo.findOrdersByUserOrderByPlacedAtDesc(user, pageable) );
		
		return "orderList";
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
