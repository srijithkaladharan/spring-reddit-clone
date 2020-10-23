package com.srijith.redditclone.exceptions;



public class UsernameNotFoundException extends RuntimeException {
	public UsernameNotFoundException(String exMessage) {
		super(exMessage);
	}
}
