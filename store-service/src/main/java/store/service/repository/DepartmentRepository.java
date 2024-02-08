package store.service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import store.service.entity.Department;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {



}
