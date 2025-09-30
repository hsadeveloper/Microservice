package authserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import authserver.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "SELECT * FROM users WHERE username = :username AND password = :password", nativeQuery = true)
	Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	

}