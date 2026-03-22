package com.yaguserver.miserver.model;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "user")
public class User implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;          
    private String username; 
    private String password;  
    //private String role;
    private String email;
    private UserType role;

    public User() {}

    // Getters y setters de tus campos reales
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    public UserType getRole() { return this.role; }
    public UserType getRoleEnum() { return this.role; }
    
    public void setRole(UserType role1) { this.role = role1; }
    
    
    
    

    // Métodos de UserDetails — Hibernate los ignora con @Transient
    @Override
    @Transient
    public String getUsername() { return username; }

    @Override
    @Transient
    public String getPassword() { return password; }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override @Transient
    public boolean isAccountNonExpired() { return true; }

    @Override @Transient
    public boolean isAccountNonLocked() { return true; }

    @Override @Transient
    public boolean isCredentialsNonExpired() { return true; }

    @Override @Transient
    public boolean isEnabled() { return true; }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}