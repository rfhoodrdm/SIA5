package tacos;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class Ingredient {

	private final String id;
	private final String name;
	private final Type type;
	
	public static enum Type {
		WRAP, 
		PROTEIN, 
		VEGGIES, 
		CHEESE, 
		SAUCE;
	}
}
