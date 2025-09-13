package userservice.entity;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
public class Role {

	@Id
	private String roleId;
	

	private String name;
	
	private String description;

	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Role(String name, String description, Set<UserRole> userRoles) {
		super();
		this.name = name;
		this.description = description;
	}


	public String getName() {
		return name;
	}

	public String getRoleId() {
		return roleId;
	}



	public String getDescription() {
		return description;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

	

}