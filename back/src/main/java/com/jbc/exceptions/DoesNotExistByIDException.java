package com.jbc.exceptions;
/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 */
public class DoesNotExistByIDException extends CustomException {

	/* attributes */
	private String beanName;
	private long id;

	/* constructor */
	public DoesNotExistByIDException(String beanName, long id) {
		this.beanName = beanName;
		this.id = id;
	}

	/* toString */
	@Override
	public String toString() {
		return "The " + beanName + " with the ID " + id + " does not exist, make sure the ID is correct";
	}
}