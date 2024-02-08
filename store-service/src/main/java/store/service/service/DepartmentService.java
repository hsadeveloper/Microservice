package store.service.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import store.service.entity.Department;
import store.service.repository.DepartmentRepository;

@Service
public class DepartmentService {

  @Autowired
  DepartmentRepository departmentRepository;

  public boolean addOrUpdate(Department department) {
    departmentRepository.save(department);
    return true;
  }


}
