package br.com.cvc.banktransfer.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.util.ObjectUtils;

import br.com.cvc.banktransfer.domain.TransferType;
import br.com.cvc.banktransfer.infra.exception.InvalidAccountException;
import br.com.cvc.banktransfer.infra.exception.ZeroFareException;

public final class TransferBuilder {

	private String ACCOUNT_PATTERN = "([A-Z0-9]){6}$";
	
	private String originAccount;

	private String destinationAccount;

	private LocalDate transferDate;

	private BigDecimal value;

	public TransferBuilder withOriginAccount(String originAccount) throws InvalidAccountException {
		if(ObjectUtils.isEmpty(originAccount) || !originAccount.matches(ACCOUNT_PATTERN)) {
			throw new InvalidAccountException("invalid-account-origin");
		}
		this.originAccount = originAccount;
		return this;
	}

	public TransferBuilder withDestinationAccount(String destinationAccount) throws InvalidAccountException {
		if(ObjectUtils.isEmpty(destinationAccount) || !destinationAccount.matches(ACCOUNT_PATTERN)) {
			throw new InvalidAccountException("invalid-account-destination");
		}
		this.destinationAccount = destinationAccount;
		return this;
	}

	public TransferBuilder withTransferDatet(LocalDate transferDate) {
		this.transferDate = transferDate;
		return this;
	}

	public TransferBuilder withValue(BigDecimal value) {
		this.value = value;
		return this;
	}

	public Transfer build() throws ZeroFareException {

		BigDecimal fare = BigDecimal.ZERO;
		final LocalDate today = LocalDate.now();

		// Account type A
		if (transferDate.isEqual(today)) {
			fare = fare.add(new BigDecimal("3")).add(value.multiply(new BigDecimal("0.03")));
			return build(fare, TransferType.A);
		}

		// Get difference between the booking date and transfer date
		final Long daysDifference =  ChronoUnit.DAYS.between(today, transferDate);
		
		// Account type B
		if (transferDate.isAfter(today) && daysDifference <= 10) {
			fare = new BigDecimal("12").multiply(new BigDecimal(daysDifference.toString()));
			return build(fare, TransferType.B);
		}		

		// Account type C
		if (daysDifference > 10 && daysDifference <= 20) {
			fare = fare.add(value.multiply(new BigDecimal("0.08")));
		} else if (daysDifference > 20 && daysDifference <= 30) {
			fare = fare.add(value.multiply(new BigDecimal("0.06")));
		} else if (daysDifference > 30 && daysDifference <= 40) {
			fare = fare.add(value.multiply(new BigDecimal("0.04")));
		} else if (daysDifference > 40 && value.compareTo(new BigDecimal("100000")) == 1) {
			fare = fare.add(value.multiply(new BigDecimal("0.02")));
		}
		
		if(fare.compareTo(BigDecimal.ZERO) == 0) {
			throw new ZeroFareException();
		}

		return build(fare, TransferType.C);
	}

	private Transfer build(BigDecimal fare, TransferType transferType) {
		return new Transfer(originAccount, destinationAccount, transferDate, value, fare, LocalDate.now(),
				transferType);
	}
	

}
