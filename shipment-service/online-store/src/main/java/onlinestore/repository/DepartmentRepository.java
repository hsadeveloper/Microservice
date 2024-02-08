package onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import onlinestore.entities.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {



}
