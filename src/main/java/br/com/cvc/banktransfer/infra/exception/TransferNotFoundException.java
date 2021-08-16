package br.com.cvc.banktransfer.infra.exception;

import lombok.Getter;

@Getter
public class TransferNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6918729233657974228L;

	private final String errorCode = "2040";
	
	public TransferNotFoundException() {
		super("transfer-not-found");
	}

}
