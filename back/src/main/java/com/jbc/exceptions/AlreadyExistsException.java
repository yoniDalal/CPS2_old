package com.jbc.exceptions;
/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 */
public class AlreadyExistsException  extends CustomException {

	/* attributes */
	private String beanName;
	private String value;

	/* constructor */
	public AlreadyExistsException(String beanName, String value)
	{
		this.beanName=beanName;
		this.value=value;
	}

	/* toString */
	@Override
	public String toString() {

		return "The "+beanName+" "+value+ " already exists";
	}
}