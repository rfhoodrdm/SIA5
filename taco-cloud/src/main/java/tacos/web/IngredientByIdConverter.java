package tacos.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

@Component
@Slf4j
public class IngredientByIdConverter implements Converter<String, Ingredient> {
	
	/** *************************************************
	 * 			Member Variables and Constants
	 *  ************************************************* */ 
	
	private IngredientRepository ingredientRepo;
	
	
	/** *************************************************
	 * 					Bean Definitions
	 *  ************************************************* */ 
	
	
	/** *************************************************
	 * 					Initialization
	 *  ************************************************* */ 
	
	@Autowired
	public IngredientByIdConverter ( IngredientRepository ingredientRepo ) {
		this.ingredientRepo = ingredientRepo;
	}

	
	/** *************************************************
	 * 						API
	 *  ************************************************* */ 
	
	@Override
	public Ingredient convert(String id) {
		//return ingredientRepo.findOne(id);
		Optional<Ingredient> maybeIngredient = ingredientRepo.findById(id);		
		
		log.info("Asked to convert ingredient from string: " + id + "\t\t Found?: " + maybeIngredient.isPresent() );
		return (maybeIngredient.isPresent() )  ?  maybeIngredient.get()  :  null;
	}
}
