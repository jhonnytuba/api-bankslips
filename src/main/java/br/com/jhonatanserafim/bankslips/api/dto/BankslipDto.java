package br.com.jhonatanserafim.bankslips.api.dto;

import br.com.jhonatanserafim.bankslips.api.enumeration.BankslipStatus;
import br.com.jhonatanserafim.bankslips.core.json.BigDecimalDeserializer;
import br.com.jhonatanserafim.bankslips.core.json.BigDecimalSerializer;
import br.com.jhonatanserafim.bankslips.core.json.LocalDateDeserializer;
import br.com.jhonatanserafim.bankslips.core.json.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class BankslipDto {

    private UUID id;

    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using =  LocalDateDeserializer.class)
    @JsonProperty("due_date")
    private LocalDate dueDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using =  LocalDateDeserializer.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("payment_date")
    private LocalDate paymentDate;

    @NotNull
    @JsonSerialize(using = BigDecimalSerializer.class)
    @JsonDeserialize(using =  BigDecimalDeserializer.class)
    @JsonProperty("total_in_cents")
    private BigDecimal totalInCents;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using = BigDecimalSerializer.class)
    @JsonDeserialize(using =  BigDecimalDeserializer.class)
    private BigDecimal fine;

    @NotEmpty
    private String customer;

    @NotNull
    private BankslipStatus status = BankslipStatus.PENDING;

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

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
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