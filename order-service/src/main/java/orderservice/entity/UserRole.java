package orderservice.entity;

import java.util.Objects;

import org.springframework.data.repository.CrudRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table (name="user_role")
public class UserRole {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "role_id")
	    private long userRoleId;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "user_id")
	    private User user;

	    @ManyToOne(fetch = FetchType.EAGER)
	    private Role role;

	    public UserRole() {
	        super();
	    }

	    public UserRole(User user, Role role) {
	        this.user = user;
	        this.role = role;
	    }

	    // Getters and setters
	    public long getUserRoleId() {
	        return userRoleId;
	    }

	    public void setUserRoleId(long userRoleId) {
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

