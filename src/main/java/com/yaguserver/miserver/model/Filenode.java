package com.yaguserver.miserver.model;

import java.io.File;
import java.nio.file.Path;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Filenode {

	  FileType type;
	  String path;
	  String name;
	  long size;
	  long modified;
	  boolean readable;
	  boolean writable;
	  boolean executable; 
	  Date modDate;
	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	  String formatedDate;
	 
	 public Filenode(Path path) {
	
		 File archivo = path.toFile();
		 
		 if(archivo.isDirectory()) {
			 this.type = FileType.DIRECTORY;
		 }
		 else if(archivo.isFile()) {
			 this.type = FileType.FILE;
		 }
		 else {
			 this.type = FileType.OTHER;
		 }
		 
		 this.setExecutable(archivo.canExecute());
		 this.setWritable(archivo.canWrite());
		 
	 }
	 
	 
	 
	 public String getFormatedDate() {
		return formatedDate;
	}

	 private Date getModDate() {
		return modDate;
	}

	 public boolean isReadable() {
		return readable;
	}
	 public void setReadable(boolean readable) {
		 this.readable = readable;
	 }
	 public boolean isWritable() {
		 return writable;
	 }
	 public void setWritable(boolean writable) {
		 this.writable = writable;
	 }
	 public boolean isExecutable() {
		 return executable;
	 }
	 public void setExecutable(boolean executable) {
		 this.executable = executable;
	 }
	 public FileType getType() {
		 return type;
	 }
	 public void setType(FileType type) {
		 this.type = type;
	 }
	 public String getPath() {
		 return path;
	 }
	 public void setPath(String string) {
		 this.path = string;
	 }

	 public String getName() {
		 return name;
	 }
	 public void setName(String name) {
		 this.name = name;
	 }
	
	 public long getSize() {
		 return size;
	 }
	 public void setSize(long size) {
		 this.size = size;
	 }
	 private long getModified() {
		 return modified;
	 }
	 public void setModified(long modified) {
		 
		 this.modified = modified;
		 this.modDate = new Date(this.modified); 
		 this.formatedDate = this.sdf.format(modDate); 
	 } 
	 
	 
	 
	  
	 
	
	
	
	
}
