package br.com.jhonatanserafim.bankslips.endpoint.provider;

import br.com.jhonatanserafim.bankslips.api.dto.BankslipDto;
import br.com.jhonatanserafim.bankslips.api.dto.BankslipPaymentDto;
import br.com.jhonatanserafim.bankslips.api.entity.Bankslip;
import br.com.jhonatanserafim.bankslips.api.enumeration.BankslipStatus;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class BankslipDataProvider {

    private TestEntityManager entityManager;

    public BankslipDataProvider(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void registerBankslipPending() {
        entityManager.merge(buildBankslipPending());
    }

    public void removeBankslipPending() {
        entityManager.remove(entityManager.find(Bankslip.class, buildBankslipPending().getId()));
    }

    public void registerBankslipPaid() {
        entityManager.merge(buildBankslipPaid());
    }

    public void removeBankslipPaid() {
        entityManager.remove(entityManager.find(Bankslip.class, buildBankslipPaid().getId()));
    }

    public void registerBankslipPaidWithFine() {
        entityManager.merge(buildBankslipPaidWithFine());
    }

    public void removeBankslipPaidWithFine() {
        entityManager.remove(entityManager.find(Bankslip.class, buildBankslipPaidWithFine().getId()));
    }

    public Bankslip buildBankslipPending() {
        Bankslip bankslip = new Bankslip();
        bankslip.setId(UUID.fromString("84e8adbf-1a14-403b-ad73-d78ae19b59bf"));
        bankslip.setDueDate(LocalDate.of(2018, 1, 1));
        bankslip.setTotalInCents(BigDecimal.valueOf(1000d));
        bankslip.setCustomer("Ford Prefect Company");
        bankslip.setStatus(BankslipStatus.PENDING);
        return bankslip;
    }

    public Bankslip buildBankslipPaid() {
        Bankslip bankslip = new Bankslip();
        bankslip.setId(UUID.fromString("c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89"));
        bankslip.setDueDate(LocalDate.of(2018, 2, 1));
        bankslip.setPaymentDate(LocalDate.of(2018, 2, 1));
        bankslip.setCustomer("Zaphod Company");
        bankslip.setStatus(BankslipStatus.PAID);
        return bankslip;
    }

    public Bankslip buildBankslipPaidWithFine() {
        Bankslip bankslip = new Bankslip();
        bankslip.setId(UUID.fromString("ef27bae7-584f-4455-897e-2cb4efc12b54"));
        bankslip.setDueDate(LocalDate.of(2018, 5, 10));
        bankslip.setPaymentDate(LocalDate.of(2018, 5, 13));
        bankslip.setTotalInCents(BigDecimal.valueOf(990d));
        bankslip.setCustomer("Ford Prefect Company");
        bankslip.setStatus(BankslipStatus.PAID);
        return bankslip;
    }

    public BankslipDto buildBankslipDtoForRegisterSuccessfully() {
        BankslipDto bankslipDto = new BankslipDto();
        bankslipDto.setDueDate(LocalDate.of(2018, 1, 1));
        bankslipDto.setTotalInCents(BigDecimal.valueOf(1000d));
        bankslipDto.setCustomer("Trillian Company");
        return bankslipDto;
    }

    public BankslipPaymentDto buildBankslipPaymentDtoForPaymentSuccessfully() {
        BankslipPaymentDto bankslipPaymentDto = new BankslipPaymentDto();
        bankslipPaymentDto.setPaymentDate(LocalDate.of(2018, 5, 13));
        return bankslipPaymentDto;
    }
}
