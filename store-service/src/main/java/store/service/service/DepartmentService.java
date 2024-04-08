package store.service.service;



import java.util.Collection;

import org.springframework.stereotype.Service;

import store.service.entity.Department;
import store.service.repository.DepartmentRepository;

@Service
public class DepartmentService {


  private final DepartmentRepository departmentRepository;
  
  
 
  public DepartmentService(DepartmentRepository departmentRepository) {
	super();
	this.departmentRepository = departmentRepository;
}

  public Collection<Department> getDepartments() {
	  
	    return departmentRepository.findAll();

  }
  
  public Department getDepartentById(Long id) {
	  
	  if (departmentRepository.existsById(id)) {
	      return departmentRepository.findById(id).get();
	    } else {
	      return null;
	    }
	  }
	  
  


  public boolean addOrUpdate(Department department) {
    departmentRepository.save(department); 
    return true;
  }


}
