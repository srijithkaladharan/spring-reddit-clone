package com.srijith.redditclone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class NotFoundExceptionHandler {
	@ExceptionHandler(value = {PostNotFoundException.class, UsernameNotFoundException.class, SubredditNotFoundException.class})
	public ResponseEntity<Object> handleApiRequestException(RuntimeException e){
		ApiException apiException = new ApiException(
												e.getMessage(),
												e,
												HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	  }
}
