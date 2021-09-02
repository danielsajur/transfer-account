package br.com.cvc.banktransfer.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import br.com.cvc.banktransfer.domain.strategy.TaxCalculator;
import br.com.cvc.banktransfer.domain.strategy.TaxCalculatorTypeA;
import br.com.cvc.banktransfer.domain.strategy.TaxCalculatorTypeB;
import br.com.cvc.banktransfer.domain.strategy.TaxCalculatorTypeC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cvc.banktransfer.domain.entity.Transfer;

@Service
public class TransferServiceImpl implements TransferService {

	@Autowired
	private TransferBookingRepository repository;

	@Override
	public List<Transfer> getBy(String originAccount) {
		return repository.getBy(originAccount);
	}

	@Override
	public Transfer add(String originAccount, String destinationAccount, LocalDate transferDate, BigDecimal value) {
		TaxCalculator taxCalculator = getTaxCalculator(transferDate, value);
		return repository.add(new Transfer(originAccount, destinationAccount, transferDate, taxCalculator));
	}

	private TaxCalculator getTaxCalculator(LocalDate transferDate, BigDecimal value) {

		final long daysDifference =  ChronoUnit.DAYS.between(LocalDate.now(), transferDate);

		if (daysDifference == 0) {
			return new TaxCalculatorTypeA(value);
		}

		if(daysDifference <= 10) {
			return new TaxCalculatorTypeB(value, transferDate);
		}

		return new TaxCalculatorTypeC(value, transferDate);
	}

	@Override
	public List<Transfer> getAll() {
		return repository.getAll();
	}

}
