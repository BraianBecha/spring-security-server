package com.yaguserver.miserver.service;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yaguserver.miserver.model.User;
import com.yaguserver.miserver.repo.IUserRepository;

@Service

public class MyUserDetailService implements UserDetailsService{
	
	@Autowired
	private IUserRepository userRepo;
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return null;
	}	
	
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

}
