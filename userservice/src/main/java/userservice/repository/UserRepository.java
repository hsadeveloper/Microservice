package userservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;

import userservice.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
//	@PostAuthorize("returnObject?.owner== authentication?.name") 
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
        
    void deleteByUsername(String username);
}