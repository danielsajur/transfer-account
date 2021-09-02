package br.com.cvc.banktransfer.domain.strategy;

import br.com.cvc.banktransfer.domain.TransferType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
abstract class AbstractTaxCalculator {

    BigDecimal tax;
    final BigDecimal value;
    final TransferType transferType;

    public AbstractTaxCalculator(BigDecimal tax, BigDecimal value, TransferType transferType) {
        this.tax = tax;
        this.value = value;
        this.transferType = transferType;
    }
    public AbstractTaxCalculator(BigDecimal value, TransferType transferType) {
        this(null, value, transferType);
    }
}
