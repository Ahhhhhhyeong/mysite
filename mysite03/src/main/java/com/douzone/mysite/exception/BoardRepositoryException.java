package com.douzone.mysite.exception;

public class BoardRepositoryException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public BoardRepositoryException(String message) {
		super(message);
	}
	
	public BoardRepositoryException() {
		super("BoardRepositoryException Occurs...");
	}
}
