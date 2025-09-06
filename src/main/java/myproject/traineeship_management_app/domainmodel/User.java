package myproject.traineeship_management_app.domainmodel;

import java.util.Collection;
import java.util.Collections;

import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User  implements UserDetails{

	@Id
	@Column(name="username", unique=true)
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Enumerated(EnumType.STRING)
    @Column(name="role", length = 20)
	private Role role;
	
    @Column(name="full_name")
    private String fullName;
	
	public User() {
		
	}
	public User(String username, String password) {
	    this.username = username;
	    this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getFullName() { 
		return fullName; 
	}
	
    public void   setFullName(String fullName) { 
    	this.fullName = fullName; 
    }
   
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
	     return Collections.singletonList(authority);
	}
    
	
}
