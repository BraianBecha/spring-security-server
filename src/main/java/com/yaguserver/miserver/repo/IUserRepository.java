package com.yaguserver.miserver.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.yaguserver.miserver.model.User;

public interface IUserRepository  extends JpaRepository<User, Long>{

	public Boolean existsByUsername(String username);

	public Optional<User> findByUsername(String username);
	
	public User findByPassword(String password);
}
