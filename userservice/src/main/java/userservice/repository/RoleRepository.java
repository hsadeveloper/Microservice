package userservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import userservice.entity.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(String name);

    Optional<Role> findByRoleId(String roleId);

    boolean existsByName(String name);

    boolean existsByRoleId(String roleId);
}