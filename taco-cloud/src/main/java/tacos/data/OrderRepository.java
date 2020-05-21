package tacos.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import tacos.Order;
import tacos.User;

//public interface OrderRepository {
//	
//	Order save (Order order);
//
//}


public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findOrdersByUserOrderByPlacedAtDesc( User user, Pageable pageable );
	
}