package userservice.entity;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "userrole")
public class UserRole {

	  @org.springframework.data.annotation.Id
	    
	    private String userRoleId;

	   
	    private User user;

	    private Role role;

	    public UserRole() {
	        super();
	    }

	    public UserRole(User user, Role role) {
	        this.user = user;
	        this.role = role;
	    }

	    // Getters and setters
	    public String getUserRoleId() {
	        return userRoleId;
	    }

	    public void setUserRoleId(String userRoleId) {
	        this.userRoleId = userRoleId;
	    }

	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }

	    public Role getRole() {
	        return role;
	    }

	    public void setRole(Role role) {
	        this.role = role;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(userRoleId);
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        UserRole other = (UserRole) obj;
	        return userRoleId == other.userRoleId;
	    }
	
	
	
}

