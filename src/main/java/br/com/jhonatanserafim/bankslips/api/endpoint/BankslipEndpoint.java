package br.com.jhonatanserafim.bankslips.api.endpoint;

import br.com.jhonatanserafim.bankslips.api.dto.BankslipDto;
import br.com.jhonatanserafim.bankslips.core.endpoint.Endpoint;
import br.com.jhonatanserafim.bankslips.api.dto.BankslipPaymentDto;
import br.com.jhonatanserafim.bankslips.api.service.BankslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/rest/bankslips", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
public class BankslipEndpoint implements Endpoint {

    @Autowired
    private BankslipService service;

    @PostMapping
    public ResponseEntity<BankslipDto> save(@Nullable @Validated @RequestBody BankslipDto dto, Errors errors) {
        if (errors.hasErrors()) {
            return unprocessableEntity();
        }
        if (dto == null) {
            return badRequest();
        }
        return created(service.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<BankslipDto>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BankslipDto> getDetails(@PathVariable("id") UUID id) {
        return service.findById(id)
                .map(this::ok)
                .orElse(notFound());
    }

    @PostMapping(value = "/{id}/payments")
    public ResponseEntity<BankslipDto> pay(@PathVariable("id") UUID id, @Nullable @Validated @RequestBody BankslipPaymentDto paymentDto, Errors errors) {
        if (errors.hasErrors()) {
            return unprocessableEntity();
        }
        if (paymentDto == null) {
            return badRequest();
        }
        if (service.isNotFound(id)) {
            return notFound();
        }
        service.pay(id, paymentDto.getPaymentDate());
        return noContent();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BankslipDto> cancel(@PathVariable("id") UUID id) {
        if (service.isNotFound(id)) {
            return notFound();
        }
        service.cancel(id);
        return noContent();
    }
}
