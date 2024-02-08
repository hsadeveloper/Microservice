package onlinestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import onlinestore.entities.Department;
import onlinestore.repository.DepartmentRepository;

@Service
public class DepartmentService {

  @Autowired
  DepartmentRepository departmentRepository;

  public boolean addOrUpdate(Department department) {
    departmentRepository.save(department);
    return true;
  }


}
