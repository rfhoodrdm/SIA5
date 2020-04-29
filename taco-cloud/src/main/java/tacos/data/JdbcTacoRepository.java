//package tacos.data;
//
//
//
//import java.sql.Timestamp;
//import java.sql.Types;
//import java.util.Arrays;
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import tacos.Ingredient;
//import tacos.Taco;
//
//@Repository
//public class JdbcTacoRepository implements TacoRepository {
//	
//	private JdbcTemplate jdbc;
//	
//	
//	@Autowired
//	public JdbcTacoRepository ( JdbcTemplate jdbc ) {
//		this.jdbc = jdbc;
//	}
//	
//
//	@Override
//	public Taco save(Taco taco) {
//		saveTacoInfo(taco);		//save taco info itself. We MUST have an id set in it for the next part.
//		
//		//iterate through and save each taco ingredient.
//		for ( Ingredient currentIngredient: taco.getIngredients() ) {
//			saveIngredientToTaco( currentIngredient, taco.getId() );
//		}
//		
//		return taco;	//save object out as was passed in
//	}
//	
//	
//	private void saveTacoInfo( Taco taco ) {
//		taco.setCreatedAt( new Date() );	//set creation timestamp field.
//		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory (
//				"INSERT INTO Taco (name, createdAt)  VALUES  (?, ?)", Types.VARCHAR, Types.TIMESTAMP);
//		pscf.setReturnGeneratedKeys(true);
//		
//		PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())) );
//		
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		jdbc.update(psc, keyHolder);
//		taco.setId(keyHolder.getKey().longValue());		//set 
//	}
//	
//	
//	private void saveIngredientToTaco(Ingredient currentIngredient, Long tacoId) {
//		jdbc.update( "INSERT INTO Taco_Ingredients (taco, ingredient) VALUES (?,? )", tacoId, currentIngredient.getId());
//		
//	}
//
//
//
//
//}
