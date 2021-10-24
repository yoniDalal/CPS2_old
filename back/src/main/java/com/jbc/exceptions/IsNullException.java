package com.jbc.exceptions;
/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 */
public class IsNullException extends CustomException {

	/* attributes */
	private String beanName;

	/* constructor */
	public IsNullException(String beanName) {
		this.beanName = beanName;
	}

	/* toString */
	@Override
	public String toString() {
		return "The " + beanName + " is NULL";
	}
}