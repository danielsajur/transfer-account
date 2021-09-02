package br.com.cvc.banktransfer.infra.exception;

import lombok.Getter;

@Getter
public class ZeroFareException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5006671305360725675L;
	
	private final String errorCode = "1872";

	public ZeroFareException() {
		super("zero-fare");
	}
}
