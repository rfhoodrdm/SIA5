//package tacos.data;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import tacos.Ingredient;
//
//@Repository
//public class JdbcIngredientRepository implements IngredientRepository {
//	
//	private JdbcTemplate jdbc;
//	
//	@Autowired
//	public JdbcIngredientRepository(JdbcTemplate jdbc ) {
//		this.jdbc = jdbc;
//	}
//
//	@Override
//	public Iterable<Ingredient> findAll() {
//		return jdbc.query("SELECT id, name, type FROM Ingredient", 
//				this::mapRowToIngredient);		//TODO determine advantage of function reference (like lambda expression) over class that implements interface.
//	}
//
//	@Override
//	public Ingredient findOne(String id) {
//		return jdbc.queryForObject("SELECT id, name, type FROM Ingredient WHERE id = ?", 
//				this::mapRowToIngredient,
//				id);
//	}
//
//	@Override
//	public Ingredient save(Ingredient ingredient) {
//		jdbc.update("INSERT INTO Ingredient (id, name, type) VALUES (?,?,?) ",
//				ingredient.getId(),
//				ingredient.getName(),
//				ingredient.getType().toString() );
//		return ingredient;
//	}
//	
//	/**
//	 * Implementation of rowMapper method.
//	 * @param rs
//	 * @param rowNum
//	 * @return
//	 * @throws SQLException
//	 */
//	private Ingredient mapRowToIngredient( ResultSet rs, int rowNum ) throws SQLException {
//		return Ingredient.builder()
//				.id( rs.getString("id") )
//				.name( rs.getString("name") )
//				.type( Ingredient.Type.valueOf( rs.getString("type")))
//				.build();
//	}
//
//}
