package br.com.cvc.banktransfer.domain.strategy;

import br.com.cvc.banktransfer.domain.TransferType;
import br.com.cvc.banktransfer.infra.exception.ZeroFareException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static java.util.Optional.ofNullable;

public class TaxCalculatorTypeC extends AbstractTaxCalculator implements TaxCalculator {

    private static final BigDecimal LIMIT_AMOUNT = BigDecimal.valueOf(100000);

    private static final String TAX_002 = "TAX_002";
    private static final String TAX_004 = "TAX_004";
    private static final String TAX_006 = "TAX_006";
    private static final String TAX_008 = "TAX_008";

    private static final Map<String, BigDecimal> taxes = new HashMap<>();

    private final BiFunction<LocalDate, BigDecimal, String> function;

    private final LocalDate dateToTransfer;

    {
        function = (date, amount) -> {

            final long daysDifference =  ChronoUnit.DAYS.between(LocalDate.now(), date);

            if(daysDifference <= 20){ return TAX_008; }
            if(daysDifference <= 30){ return TAX_006; }
            if(daysDifference <= 40){ return TAX_004; }
            if(amount.compareTo(LIMIT_AMOUNT) > 0){ return TAX_002; }

            return "NO_TAX";
        };

        taxes.put(TAX_008, BigDecimal.valueOf(0.08));
        taxes.put(TAX_006, BigDecimal.valueOf(0.06));
        taxes.put(TAX_004, BigDecimal.valueOf(0.04));
        taxes.put(TAX_002, BigDecimal.valueOf(0.02));
    }

    public TaxCalculatorTypeC(BigDecimal value, LocalDate dateToTransfer) {
        super(value, TransferType.C);
        this.dateToTransfer = dateToTransfer;
        tax = taxes.get(function.apply(dateToTransfer, value));
    }

    public BigDecimal calculate() {
        return ofNullable(tax).orElseThrow(ZeroFareException::new).multiply(value);
    }

}
