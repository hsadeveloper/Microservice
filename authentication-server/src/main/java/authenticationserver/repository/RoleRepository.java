package authenticationserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import authenticationserver.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);
}