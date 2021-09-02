package br.com.cvc.banktransfer.domain.entity;

import br.com.cvc.banktransfer.domain.TransferType;
import br.com.cvc.banktransfer.domain.strategy.TaxCalculator;
import br.com.cvc.banktransfer.infra.exception.ZeroFareException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;


@Data
@EqualsAndHashCode
public class Transfer implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -6413131886630105448L;

	private String id = UUID.randomUUID().toString();

	private final String originAccount;

	private final String destinationAccount;

	private final LocalDate transferDate;

	private final BigDecimal value;

	private final BigDecimal fare;

	private final LocalDate booking;

	private final TransferType type;

	public Transfer(String originAccount, String destinationAccount, LocalDate transferDate, TaxCalculator calculator) {
		super();
		this.originAccount = originAccount;
		this.destinationAccount = destinationAccount;
		this.transferDate = transferDate;
		this.value = calculator.getValue();
		this.fare = ofNullable(calculator.calculate()).orElseThrow(ZeroFareException::new);
		this.booking = LocalDate.now();
		this.type = calculator.getTransferType();
	}


}
