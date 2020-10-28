package com.srijith.redditclone.exceptions;


@SuppressWarnings("serial")
public class UsernameNotFoundException extends RuntimeException {

	public UsernameNotFoundException(String message) {
		super(message);
	}
	
	public UsernameNotFoundException(String message, Throwable e) {
		super(message, e);
	}
}
