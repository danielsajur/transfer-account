package br.com.cvc.banktransfer.domain.strategy;

import br.com.cvc.banktransfer.domain.TransferType;

import java.math.BigDecimal;

public interface TaxCalculator {
    BigDecimal calculate();
    BigDecimal getValue();
    TransferType getTransferType();

}
