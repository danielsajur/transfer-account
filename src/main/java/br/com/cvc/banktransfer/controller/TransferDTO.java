package br.com.cvc.banktransfer.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class TransferDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6839965234177316401L;

	private String originAccount;
	
	private String destinationAccount;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate transferDate;
	
	private BigDecimal value;

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		builder.append("\"originAccount\":");
		builder.append("\"" + originAccount + "\"");
		builder.append(", \"destinationAccount\":");
		builder.append("\"" + destinationAccount + "\"");
		builder.append(", \"transferDate\":");
		builder.append("\"" + transferDate + "\"");
		builder.append(", \"value\":");
		builder.append("\""+ value + "\"");
		builder.append("}");
		return builder.toString();
	}

	
}
