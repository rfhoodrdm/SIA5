package tacos.web;

import java.util.ArrayList;
import java.util.Arrays;
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


import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Taco;
import tacos.data.IngredientRepository;

import static tacos.Ingredient.Type;


@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepo;
	
	@Autowired
	public DesignTacoController ( IngredientRepository ingredientRepo ) {
		this.ingredientRepo = ingredientRepo;
	}
	
	
	@GetMapping
	public String showDesignForm(Model model) {
		
		//fetch ingredients
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach( i -> ingredients.add(i) );
		
		
		//add to model
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients, type));
		}
		
		//add something to hold the taco design
		model.addAttribute("design", new Taco() );
		
		//name of the view
		return "design";
	}
	
	private List<Ingredient> filterByType( List <Ingredient> ingredients, Type type) {
		return ingredients.stream()
			.filter( ingredient -> ingredient.getType().equals(type) )
			.collect(Collectors.toList() );
	}
	
	
	@PostMapping
	public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors) {
		//check for errors. If so, then go back to the design page.
		if ( errors.hasErrors() ) {
			return "design";
		}
		
		//save the taco design. 
		//done later.
		log.info("Processing design: " + design);
		
		return "redirect:/orders/current";
	}

}
