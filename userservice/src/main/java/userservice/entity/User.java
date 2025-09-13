package userservice.entity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "user")
public class User implements UserDetails {

    @Id
    private String userId;

    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String username;
    private String email;
    private String password;
    private Date created;
 
    private List<Role> roles;

    public User() {
        super();
    }

    public User(String userId, String firstName, String lastName, String username, String email, String password,
            Date created, List<Role> roles) {
        super();
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.created = created;
        this.roles = roles;
    }

    // Implement UserDetails methods

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement account expiration logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement account locking logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement credentials expiration logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement enable/disable logic if needed
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreated() {
        return created;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> list) {
        this.roles = list;
    }
}