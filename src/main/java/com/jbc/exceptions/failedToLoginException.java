package com.jbc.exceptions;
/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 */
public class failedToLoginException extends CustomException {

	/* attributes */
	private String message;

	/* constructor */
	public failedToLoginException(String message) {
		this.message=message;
	}

	/* toString */
	@Override
	public String toString() {
		return message;
	}
}