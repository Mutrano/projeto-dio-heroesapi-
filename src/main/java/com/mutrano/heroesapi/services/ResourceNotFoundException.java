package com.mutrano.heroesapi.services;

public class ResourceNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	private final String request;
	public ResourceNotFoundException(String message,String request) {
		super(message);
		this.request=request;
	}
	public String getRequest() {
		return request;
	}	
}
