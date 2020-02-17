package com.cursomc.services.exceptions;

public class DataIntegrityException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String msg) {
		super("Cannot delete! Object is nested.");
	}

}
