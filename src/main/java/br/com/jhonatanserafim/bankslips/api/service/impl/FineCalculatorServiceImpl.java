package br.com.jhonatanserafim.bankslips.api.service.impl;

import br.com.jhonatanserafim.bankslips.api.dto.BankslipDto;
import br.com.jhonatanserafim.bankslips.api.service.FineCalculatorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class FineCalculatorServiceImpl implements FineCalculatorService {

    private static final BigDecimal WITHOUT_FINE = BigDecimal.ZERO;
    private static final BigDecimal FINE_UNTIL_10_DAYS = BigDecimal.valueOf(0.005);
    private static final BigDecimal FINE_AFTER_10_DAYS = BigDecimal.valueOf(0.01);

    @Override
    public void calculate(BankslipDto bankslipDto) {
        bankslipDto.setFine(calculate(bankslipDto.getDueDate(), bankslipDto.getTotalInCents()));
    }

    @Override
    public BigDecimal calculate(LocalDate dueDate, BigDecimal totalInCents) {
        long betweenInDays = ChronoUnit.DAYS.between(dueDate, getCurrentDate());

        if (betweenInDays == 0L) {
            return WITHOUT_FINE;
        }

        BigDecimal fine;
        if (betweenInDays <= 10L) {
            fine = FINE_UNTIL_10_DAYS;
        } else {
            fine = FINE_AFTER_10_DAYS;
        }

        return totalInCents.multiply(fine).multiply(BigDecimal.valueOf(betweenInDays));
    }

    LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
