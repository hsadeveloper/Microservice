package com.example.authcommon.repository;

import com.example.authcommon.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    @Query(nativeQuery = true, value = "select * from user_details where username = :username and password= :password")
    Optional<UserDetails> findByUsernameAndPassword(String username, String password);

    @Query(nativeQuery = true, value = "select * from user_details where username = :username")
    Optional<UserDetails> findByUsername(String username);
}
