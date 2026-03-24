package com.yaguserver.miserver.controller;


import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.yaguserver.miserver.model.Filenode;
import com.yaguserver.miserver.model.User;
import com.yaguserver.miserver.model.UserType;
import com.yaguserver.miserver.repo.IUserRepository;
import com.yaguserver.miserver.service.Fileservice;
import com.yaguserver.miserver.service.JwtService;
import com.yaguserver.miserver.service.MyUserDetailService;

import jakarta.annotation.Resource;



@RestController
@RequestMapping
//@Controller
public class Controller {
	
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtService jwtService;
    @Autowired private MyUserDetailService userDetailsService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private IUserRepository userRepo;
    @Autowired private Fileservice fileService;
    @Autowired private User user;
	
	record AuthResponse(String atoken, String ausername, String arole) {};
	record LoginRequest(String username, String password) {};
	record SignupRequest(String username, String password, String role) {};
	record downloadRequest(String path) {};

	private Fileservice servicioDeArchivos;
	


	public Controller(Fileservice servicioDeArchivos) {
		this.servicioDeArchivos = servicioDeArchivos;
	}
	
	
	  @GetMapping("/prueba")
	  public String index() {
		  System.out.println("prueba hit");
	    return "hola desde el servidor";   
	        
	    
	  }
	  	
	  
	  public Fileservice getServicioDeArchivos() {
		return servicioDeArchivos;
	}


	  public void setServicioDeArchivos(Fileservice servicioDeArchivos) {
		  this.servicioDeArchivos = servicioDeArchivos;
	  }


	  @GetMapping("/obtenerlista")
	  public List<Filenode> obtenerLista() throws IOException {		  
		 List<Filenode> lista = this.servicioDeArchivos.getNodes("../../Archivos-Simulados");			
		 return lista;
	  }
	  
	  @GetMapping("/obtener")
	  public ResponseEntity<InputStreamResource> descargarArchivo(@RequestBody downloadRequest request) throws IOException {	  
		  return fileService.descargarPorPath(request.path);   
	  }
	  
	  @GetMapping("/recibir")
	  public String retornar() throws IOException {
		  String respuesta;
		  StringBuilder sb = new StringBuilder();
		 List<Filenode> lista = this.servicioDeArchivos.getNodes("../../Archivos-Simulados");
		 for(Filenode x : lista) {
			 sb.append(x.getName()) ;
			 sb.append("  <br>  ");
			 sb.append(x.getFormatedDate());
			 sb.append("  <br>   ");
			 sb.append(x.getType());
			 sb.append("  <br>  ");
			 sb.append("  <br>  ");
			 
		 }
		 respuesta = sb.toString();
		 return respuesta;
	  }
	  
 		  
	  @PostMapping("/login")
	    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
		  System.out.println("login hit");
	        authManager.authenticate(
	            new UsernamePasswordAuthenticationToken(request.username(), request.password())
	        );
	        UserDetails user = userDetailsService.loadUserByUsername(request.username());
	        String token = jwtService.generateToken(user);
	        return ResponseEntity.ok(Map.of("token", token));
	    }
	   	  
	  	  
	  @PostMapping("/signup")
	  public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request) {		
		 // User user = new User();				  		  		  
	      if (userRepo.existsByUsername(request.username)) {
	    	  System.out.println("user name exists");
	          return ResponseEntity.badRequest().body(null);
	      }else
	      {	    	  
	      	  System.out.println("ok, username does not exist before " + request.username + "  " + request.username());	    	  
	      }	  
	      
	      user.setUsername(request.username);	     
	      user.setPassword(passwordEncoder.encode(request.password()));	  
	      
	      if(request.role == null || request.role.isEmpty() ) {
	    	  System.out.println("role llegó null");
	    	  user.setRole(UserType.USER);
	      }else {
	      user.setRole(UserType.valueOf(request.role));
	      System.out.println("role llegó con " + request.role);
	      }
	      userRepo.save(user);

	      String token = jwtService.generateToken(user);
	      return ResponseEntity.ok(new AuthResponse(token, user.getUsername(), user.getRole().toString()));
	  }
	  
	  
	
	  
}










