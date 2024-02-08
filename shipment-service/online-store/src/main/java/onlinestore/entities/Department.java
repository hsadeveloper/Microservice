package onlinestore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department")
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "department_Id")
  private Long departmentId;

  @Column(name = "department_name")
  private String departmentName;

@Override
public String toString() {
	return "Department [departmentId=" + departmentId + ", departmentName=" + departmentName + "]";
}
  
  
  
  


//  public Department(String departmentName) {
//    super();
//    this.departmentName = departmentName;
//  }
//
//	public Long getDepartmentId() {
//		return departmentId;
//	}
//	
//	public void setDepartmentId(Long departmentId) {
//		this.departmentId = departmentId;
//	}
//	
//	public String getDepartmentName() {
//		return departmentName;
//	}
//	
//	public void setDepartmentName(String departmentName) {
//		this.departmentName = departmentName;
//	}
//
//	public Department() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public String toString() {
//		return "Department [departmentId=" + departmentId + ", departmentName=" + departmentName + "]";
	//}
  
    
   

}


