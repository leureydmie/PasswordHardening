package com.rpecebou.exceptions;

/**
 * 
 * @author rpecebou
 *
 */

public class InvalidInputException extends Exception {

	private static final long serialVersionUID = -3608950673860221656L;

	public InvalidInputException() {
		super("Invalid input as been provided");
	}

}
