package br.com.cvc.banktransfer.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.cvc.banktransfer.domain.entity.Transfer;

public interface TransferService {
	
	Transfer add(String originAccount, String destinationAccount, LocalDate transferDate, BigDecimal value);
	
	List<Transfer> getBy(String originAccount);
	
	List<Transfer> getAll();
}
