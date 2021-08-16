package br.com.cvc.banktransfer.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cvc.banktransfer.domain.entity.Transfer;
import br.com.cvc.banktransfer.domain.entity.TransferBuilder;

@Service
public class TransferServiceImpl implements TransferService {

	@Autowired
	private TransferBookingRepository repository;

	@Override
	public List<Transfer> getBy(String originAccount) {
		return repository.getBy(originAccount);
	}

	@Override
	public Transfer add(String originAccount, String destinationAccount, LocalDate transferDate, BigDecimal value) throws Exception {
		Transfer transfer = new TransferBuilder()
							.withOriginAccount(originAccount)
							.withDestinationAccount(destinationAccount)
							.withTransferDatet(transferDate)
							.withValue(value)
							.build();
		repository.add(transfer);
		return transfer;
	}

	@Override
	public List<Transfer> getAll() {
		return repository.getAll();
	}

}
