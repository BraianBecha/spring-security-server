package com.yaguserver.miserver.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yaguserver.miserver.model.Filenode;

import java.util.ArrayList;
import java.util.List;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;



@Service
public class Fileservice {
	
	
	

	//private Path ruta = Paths.get("../../Archivos-Simulados");
	
	
	
	public List<String> listaderutas = new ArrayList<>();
   
	public void listarDirectorios(String nombreDirectorio) throws IOException {	
			Path ruta2 = Paths.get(nombreDirectorio);
			System.out.println("llamada a listarDirectorios() directorio es: " + nombreDirectorio);	
	try(DirectoryStream<Path> stream = Files.newDirectoryStream(ruta2)){
				System.out.println("-----------------------------------------------------------------");
				for(Path element: stream) {							
					System.out.println(element.getFileName().toString());											
				}
				System.out.println("-----------------------------------------------------------------");
				
	}catch (IOException x) {
	    System.err.println(x);
	
						}
	
	
																				}
	
	public List<Filenode> getNodes(String nombreDirectorio) throws IOException{
		
			
		List<Filenode> nodelist = new ArrayList<>();
		
		Path ruta = Paths.get(nombreDirectorio);
		
		try {
			
			DirectoryStream<Path> stream = Files.newDirectoryStream(ruta); 
			
			for(Path element: stream) {				
				
				File file = element.toFile(); 				
				Filenode anode = new Filenode(element);	
				
				anode.setName(element.getFileName().toString());
				anode.setModified(file.lastModified());
				anode.setExecutable(file.canExecute());
				anode.setReadable(file.canRead());
				anode.setExecutable(file.canExecute());
				anode.setPath(file.getAbsolutePath());
				
				nodelist.add(anode);
									
			}
			stream.close();
		}
		catch(IOException x) {
			System.out.println(x);
		}
		
		return nodelist;
		
		
		
	}

	public ResponseEntity<InputStreamResource> descargarPorPath(String path) throws FileNotFoundException {
		
		   File file = new File(path);
		      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		    
		      return ResponseEntity.ok()
		              .contentLength(file.length())	              
		              .body(resource);
		      		  
		
	}



}
