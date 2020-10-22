package com.srijith.redditclone.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String exMessage) {
    	super(exMessage);
    }
}
