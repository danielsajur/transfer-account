package br.com.cvc.banktransfer.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

import br.com.cvc.banktransfer.domain.TransferType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Transfer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6413131886630105448L;

	private String id = UUID.randomUUID().toString();
	
	private String originAccount;
	
	private String destinationAccount;
	
	private LocalDate transferDate;
	
	private BigDecimal value;
	
	private BigDecimal fare;

	private LocalDate booking;
	
	private TransferType type;

	public Transfer() {
	}
	
	public Transfer(String originAccount, String destinationAccount, LocalDate transferDate, BigDecimal value,
			BigDecimal fare, LocalDate booking, TransferType type) {
		super();
		this.originAccount = originAccount;
		this.destinationAccount = destinationAccount;
		this.transferDate = transferDate;
		this.value = value;
		this.fare = fare.setScale(0,RoundingMode.HALF_EVEN);
		this.booking = booking;
		this.type = type;
	}
		
	
}
