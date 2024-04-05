package store.service.controller;

import java.util.Collection;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import store.service.entity.Department;
import store.service.service.DepartmentService;

@RestController
@RequestMapping("/dept")
public class DeptController {
	
   private final DepartmentService departmentService;
   
 
   public DeptController(DepartmentService departmentService) {
	super();
	this.departmentService = departmentService;
}
   
   @GetMapping("/") 
   private Collection<Department> getDeptmartments (){
	return departmentService.getDepartments();  
   }
   
  
	

  @PostMapping("/")
   public Boolean createDept(@Validated @RequestBody Department dept) {
	departmentService.addOrUpdate(dept);
	return true;	  
   }
	
	

}
