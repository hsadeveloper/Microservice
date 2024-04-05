package store.service.entity;



import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @NotNull
  @NotBlank
  @Column(unique=true, name = "department_name")
  private String departmentName;
  
  @Column(name = "department_tag")
  private String departmenttag;
  
  /*Inverse side - readonly*/
  @OneToMany (mappedBy ="department")
  private Set<Product> product;
  

@Override
public String toString() {
	return "Department [departmentId=" + departmentId + ", departmentName=" + departmentName + "]";
}
  
     
   

}


