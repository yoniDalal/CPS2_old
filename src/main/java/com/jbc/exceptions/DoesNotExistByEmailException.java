package com.jbc.exceptions;
/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 */
public class DoesNotExistByEmailException extends CustomException {

	/* attributes */
	private String beanName;
	private String email;

	/* constructor */
	public DoesNotExistByEmailException(String beanName, String email) {
		this.beanName = beanName;
		this.email=email;
	}

	/* toString */
	@Override
	public String toString() {
		return "The " + beanName + " with the Email " + email + " does not exist, make sure the Email is correct";
	}
}