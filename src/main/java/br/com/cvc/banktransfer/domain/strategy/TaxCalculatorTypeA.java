package br.com.cvc.banktransfer.domain.strategy;

import br.com.cvc.banktransfer.domain.TransferType;

import java.math.BigDecimal;

public class TaxCalculatorTypeA extends AbstractTaxCalculator implements TaxCalculator {

    final BigDecimal fixedTax = BigDecimal.valueOf(3);

    public TaxCalculatorTypeA(BigDecimal value) {
        super(BigDecimal.valueOf(0.03), value, TransferType.A);
    }

    public BigDecimal calculate() {
        return fixedTax.add(value.multiply(tax));
    }

}
