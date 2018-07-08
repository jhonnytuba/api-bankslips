package br.com.jhonatanserafim.bankslips.api.service.impl;

import br.com.jhonatanserafim.bankslips.api.dto.BankslipDto;
import br.com.jhonatanserafim.bankslips.api.entity.Bankslip;
import br.com.jhonatanserafim.bankslips.api.enumeration.BankslipStatus;
import br.com.jhonatanserafim.bankslips.api.repository.BankslipRepository;
import br.com.jhonatanserafim.bankslips.api.service.BankslipService;
import br.com.jhonatanserafim.bankslips.api.mapper.BankslipMapper;
import br.com.jhonatanserafim.bankslips.api.service.FineCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankslipServiceImpl implements BankslipService {

    @Autowired
    private BankslipRepository repository;

    @Autowired
    private BankslipMapper mapper;

    @Autowired
    private FineCalculatorService fineCalculatorService;

    @Override
    public BankslipDto save(BankslipDto dto) {
        Bankslip bankslip = repository.save(mapper.to(dto));
        return mapper.to(bankslip);
    }

    @Override
    public List<BankslipDto> findAll() {
        return mapper.toList(repository.findAll());
    }

    @Override
    public Optional<BankslipDto> findById(UUID id) {
        return repository.findById(id).map(entity -> {
            BankslipDto dto = mapper.to(entity);
            fineCalculatorService.calculate(dto);
            return dto;
        });
    }

    @Override
    public boolean isNotFound(UUID id) {
        return !findById(id).isPresent();
    }

    @Override
    public void pay(UUID id, LocalDate paymentDate) {
        repository.findById(id).ifPresent(bankslip -> {
            bankslip.setPaymentDate(paymentDate);
            bankslip.setStatus(BankslipStatus.PAID);
            repository.save(bankslip);
        });
    }

    @Override
    public void cancel(UUID id) {
        repository.findById(id).ifPresent(bankslip -> {
            bankslip.setStatus(BankslipStatus.CANCELED);
            repository.save(bankslip);
        });
    }
}
