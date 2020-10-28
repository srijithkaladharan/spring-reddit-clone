package com.srijith.redditclone.exceptions;

@SuppressWarnings("serial")
public class SpringRedditException extends RuntimeException {
	
	public SpringRedditException(String message) {
		super(message);
	}
	
	public SpringRedditException(String message, Throwable e) {
		super(message, e);
	}

}
