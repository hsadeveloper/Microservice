package onlinestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import onlinestore.entities.Department;
import onlinestore.service.DepartmentService;

@RestController
@RequestMapping("/dep")

public class DepartmentControler {

  @Autowired
  DepartmentService departmentService;

  @PostMapping(path = "/new", consumes = {"application/json", "application/xml"},
      produces = {"application/json", "application/xml"})
  public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
    Department departmentObj = new Department();

    departmentObj.setDepartmentId(department.getDepartmentId());
    departmentObj.setDepartmentName(department.getDepartmentName());

    departmentService.addOrUpdate(departmentObj);

    return new ResponseEntity<>(department, HttpStatus.CREATED);

  }


}
