package br.com.cvc.banktransfer.infra.exception;

import lombok.Getter;

@Getter
public class InvalidAccountException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3801084875074667108L;
	
	private final String errorCode = "7456";

	public InvalidAccountException(String message) {
		super(message);
	}
}
