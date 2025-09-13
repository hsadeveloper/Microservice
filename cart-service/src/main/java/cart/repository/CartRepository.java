package cart.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import cart.model.Cart;

public interface CartRepository extends MongoRepository <Cart, String> {

	Optional<Cart> findById(String userId);
}