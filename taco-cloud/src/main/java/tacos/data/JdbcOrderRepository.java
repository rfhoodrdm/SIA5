package tacos.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import tacos.Order;
import tacos.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert tacoOrderInserter;
	private ObjectMapper objectMapper;
	
	@Autowired
	public JdbcOrderRepository ( JdbcTemplate jdbc ) {
		this.orderInserter = new SimpleJdbcInsert (jdbc)
				.withTableName("Taco_Order")
				.usingGeneratedKeyColumns("id");
		
		this.tacoOrderInserter = new SimpleJdbcInsert (jdbc)
				.withTableName("Taco_Order_Tacos");
		
		this.objectMapper = new ObjectMapper();
	}
	
	
	@Override
	public Order save(Order order) {
		saveOrderDetails(order);
		for ( Taco currentTaco : order.getTacoList() ) {
			saveTacoToOrder( currentTaco, order.getId() );
		}
		
		return order;	//same object out as was passed in.
	}

	
	private void saveOrderDetails(Order order) {
		order.setPlacedAt(new Date());		//set date creation field.	
		Map<String, Object> values = objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt() );	//overwrite placedAt value, because objectMapper wants to make this a long, which is not compatible with the table field type.
		
		long orderId = orderInserter.executeAndReturnKey(values).longValue();	//key/value mapping insert. Get back generated key value.
		order.setId(orderId);				//set order id field.
	}
	
	
	private void saveTacoToOrder(Taco currentTaco, Long orderId) {
		Map<String, Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", currentTaco.getId());
		tacoOrderInserter.execute(values);
	}




}
