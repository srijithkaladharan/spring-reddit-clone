package com.srijith.redditclone.exceptions;


@SuppressWarnings("serial")
public class PostNotFoundException extends RuntimeException {
	
	public PostNotFoundException(String message) {
		super(message);
    }
	
	public PostNotFoundException(String message,Throwable e) {
		super(message, e);
	}
}
