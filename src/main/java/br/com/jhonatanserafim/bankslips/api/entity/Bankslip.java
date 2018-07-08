package br.com.jhonatanserafim.bankslips.api.entity;

import br.com.jhonatanserafim.bankslips.api.enumeration.BankslipStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Entity
public class Bankslip implements Serializable {

    @Id
    private UUID id;

    private LocalDate dueDate;
    private LocalDate paymentDate;
    private BigDecimal totalInCents;
    private String customer;

    @Enumerated(EnumType.STRING)
    private BankslipStatus status;

    @PrePersist
    public void prePersist() {
        setId(Optional.ofNullable(getId()).orElse(UUID.randomUUID()));
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getTotalInCents() {
        return totalInCents;
    }

    public void setTotalInCents(BigDecimal totalInCents) {
        this.totalInCents = totalInCents;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BankslipStatus getStatus() {
        return status;
    }

    public void setStatus(BankslipStatus status) {
        this.status = status;
    }
}