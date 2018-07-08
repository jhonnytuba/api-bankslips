package br.com.jhonatanserafim.bankslips.api.service;

import br.com.jhonatanserafim.bankslips.api.dto.BankslipDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface FineCalculatorService {

    void calculate(BankslipDto bankslipDto);

    BigDecimal calculate(LocalDate dueDate, BigDecimal totalInCents);
}
