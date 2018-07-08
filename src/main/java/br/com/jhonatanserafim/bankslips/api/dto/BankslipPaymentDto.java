package br.com.jhonatanserafim.bankslips.api.dto;

import br.com.jhonatanserafim.bankslips.core.json.LocalDateDeserializer;
import br.com.jhonatanserafim.bankslips.core.json.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class BankslipPaymentDto {

    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using =  LocalDateDeserializer.class)
    @JsonProperty("payment_date")
    private LocalDate paymentDate;

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}