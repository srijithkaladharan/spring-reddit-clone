package com.srijith.redditclone.exceptions;

@SuppressWarnings("serial")
public class SubredditNotFoundException extends RuntimeException{

	public SubredditNotFoundException(String message) {
	        super(message);
	  }
	
	public SubredditNotFoundException(String message, Throwable e) {
        super(message, e);
  }
}
