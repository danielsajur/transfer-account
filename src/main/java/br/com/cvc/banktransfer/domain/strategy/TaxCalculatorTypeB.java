package br.com.cvc.banktransfer.domain.strategy;

import br.com.cvc.banktransfer.domain.TransferType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TaxCalculatorTypeB extends AbstractTaxCalculator implements TaxCalculator {

    private final LocalDate dateToTransfer;

    public TaxCalculatorTypeB(BigDecimal value, LocalDate dateToTransfer) {
        super(BigDecimal.valueOf(12), value, TransferType.B);
        this.dateToTransfer = dateToTransfer;
    }

    public BigDecimal calculate() {
        final long daysDifference =  ChronoUnit.DAYS.between(LocalDate.now(), dateToTransfer);
        return value.add(tax.multiply(BigDecimal.valueOf(daysDifference)));
    }
}
