package com.mutrano.heroesapi.resources.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

	private List<FieldMessage> errors = new ArrayList<>();
	

	public ValidationError() {
		super();
	}
	public ValidationError(Instant timestamp, Integer status, String error, String path, String message) {
		super(timestamp, status, error, path, message);
		
	}
	public List<FieldMessage> getErrors() {
		return errors;
	}
	public void addError(String fieldName, String message) {
		getErrors().add(new FieldMessage(fieldName, message));
	}

	

	
}
