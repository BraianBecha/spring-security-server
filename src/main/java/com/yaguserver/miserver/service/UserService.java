package com.yaguserver.miserver.service;

import java.util.List;
import java.util.Optional;

import com.yaguserver.miserver.model.User;

public interface UserService {

	 	List<User> listarUser();
	    Optional<User> obtenerUserPorId(Long id);
	    User ObtenerUserPorNombre(String name);
	    User guardarUser(User user);
	    User actualizarUser(Long id, User user);
	    void eliminarUser(Long id);	
	

}
