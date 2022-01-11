package com.book.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {
	@ExceptionHandler(BookNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handlesBookNotFound(BookNotFoundException bookNotFoundException,
			HttpServletRequest request) {

		Map<String, String> response = new HashMap<>();

		response.put("service", "book");
		response.put("timestamp", new Date().toString());
		response.put("error", bookNotFoundException.getMessage());
		response.put("status", HttpStatus.NOT_FOUND.name());
		return response;
	}

	@ExceptionHandler(BookAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, String> handlesBookAlredyExist(BookAlreadyExistsException bookAlredyExistException,
			HttpServletRequest request) {

		Map<String, String> response = new HashMap<>();

		response.put("service", "book");
		response.put("timestamp", new Date().toString());
		response.put("error", bookAlredyExistException.getMessage());
		response.put("status", HttpStatus.CONFLICT.name());
		return response;
	}

}
