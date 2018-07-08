package br.com.jhonatanserafim.bankslips.api.repository;

import br.com.jhonatanserafim.bankslips.api.entity.Bankslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public interface BankslipRepository extends JpaRepository<Bankslip, UUID> {

}
