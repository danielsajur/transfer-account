package br.com.cvc.banktransfer.infra.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.cvc.banktransfer.domain.entity.Transfer;
import lombok.Data;

@Data
@JsonPropertyOrder({"timestamp","error","message", "data"})
public class Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9053913218310018307L;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String error;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Transfer> data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;

//	@JsonFormat(pattern = "yyyy-MM-SS HH:mm:ss")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final LocalDateTime timestamp;

	public Response() {
		this.timestamp = LocalDateTime.now();
	}
	
	public Response(String message) {
		this();
		this.message = message;
	}

	public Response(String error, String message) {
		this(message);
		this.error = error;
	}

	public Response(List<Transfer> data) {
		this();
		this.data = data;
	}
	
	public Response(String message, List<Transfer> data) {
		this(message);
		this.data = data;
	}

}
