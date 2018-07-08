package br.com.jhonatanserafim.bankslips.api.service;

import br.com.jhonatanserafim.bankslips.api.dto.BankslipDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankslipService {

    BankslipDto save(BankslipDto dto);

    List<BankslipDto> findAll();

    Optional<BankslipDto> findById(UUID id);

    boolean isNotFound(UUID id);

    void pay(UUID id, LocalDate paymentDate);

    void cancel(UUID id);
}
