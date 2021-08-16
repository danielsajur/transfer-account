package br.com.cvc.banktransfer.domain;

import java.util.List;

import br.com.cvc.banktransfer.domain.entity.Transfer;

public interface TransferBookingRepository {
	
	void add(Transfer transfer);
	
	List<Transfer> getBy(String originAccount);

	List<Transfer> getAll();
}
