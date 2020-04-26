package tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepo;
	private final TacoRepository tacoRepository;
	
	@Autowired
	public DesignTacoController ( IngredientRepository ingredientRepo, TacoRepository tacoRepository ) {
		this.ingredientRepo = ingredientRepo;
		this.tacoRepository = tacoRepository;
	}
	
	@ModelAttribute(name = "taco")
	  public Taco taco() {
	    return new Taco();
	  }
	
	@ModelAttribute(name = "order")
	  public Order order() {
	    return new Order();
	  }
	
	
	@GetMapping
	public String showDesignForm(Model model) {
		
		//fetch ingredients
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach( i -> ingredients.add(i) );
		
		
		//add to model
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute( type.toString().toLowerCase(), filterByType(ingredients, type));
		}
		
		//name of the view
		return "design";
	}
	
	
	private List<Ingredient> filterByType( List <Ingredient> ingredients, Type type) {
		return ingredients.stream()
			.filter( ingredient -> ingredient.getType().equals(type) )
			.collect(Collectors.toList() );
	}
	
	
	@PostMapping
	public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order) {
		//check for errors. If so, then go back to the design page.
		if ( errors.hasErrors() ) {
			return "design";
		}
		
		//save the taco design and add it to the order.
		tacoRepository.save(taco);
		order.addTaco(taco);
		log.info("Processing design: " + taco);
		
		
		return "redirect:/orders/current";
	}

}




