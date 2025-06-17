package com.org.userservice.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.userservice.entity.User;


@Repository
public interface UserRepository extends  CrudRepository <User, Long> {
	
	
	User findByUsername(String username );
	
	User findByEmail(String email);
	
	@Query(value = "SELECT * FROM user WHERE email LIKE CONCAT(:value, '%')", nativeQuery = true)
	User searchByEmail(@Param("value") String value);



	

}