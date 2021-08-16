package br.com.cvc.banktransfer.infra.exception;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cvc.banktransfer.infra.response.Response;

@Configuration
@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {	

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(value = TransferNotFoundException.class)
	public ResponseEntity<Response> transferNotFoundException(TransferNotFoundException e) {		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@ExceptionHandler(value = ZeroFareException.class)
	public ResponseEntity<Response> zeroFareException(ZeroFareException e) {		
		String message = messageSource.getMessage(e.getMessage(), null, Locale.getDefault());
		Response response = new Response(e.getErrorCode(), message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(value = InvalidAccountException.class)
	public ResponseEntity<Response> invalidAccountException(InvalidAccountException e) {
		String message = messageSource.getMessage(e.getMessage(), null, Locale.getDefault());
		Response response = new Response(e.getErrorCode(), message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
}
