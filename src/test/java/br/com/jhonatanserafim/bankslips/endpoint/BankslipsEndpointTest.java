package br.com.jhonatanserafim.bankslips.endpoint;

import br.com.jhonatanserafim.bankslips.api.dto.BankslipDto;
import br.com.jhonatanserafim.bankslips.api.dto.BankslipPaymentDto;
import br.com.jhonatanserafim.bankslips.api.entity.Bankslip;
import br.com.jhonatanserafim.bankslips.api.service.FineCalculatorService;
import br.com.jhonatanserafim.bankslips.core.util.CurrencyFormatter;
import br.com.jhonatanserafim.bankslips.core.util.DateFormatter;
import br.com.jhonatanserafim.bankslips.endpoint.provider.BankslipDataProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class BankslipsEndpointTest {

    private static final String URI_BANKSLIPS = "/rest/bankslips";
    private static final String URI_PAYMENTS = "/payments";
    private static final String ID_BANKSLIP_NON_EXISTENT = "/84e8adbf-1a14-403b-ad73-d78ae19b59bf";

    @Autowired
    private FineCalculatorService fineCalculatorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager entityManager;

    private BankslipDataProvider dataProvider;

    @Before
    public void before() {
        this.dataProvider = new BankslipDataProvider(entityManager);
    }

    @Test public void
    should_return_201_when_register_a_bankslip() throws Exception {
        BankslipDto bankslipDto = dataProvider.buildBankslipDtoForRegisterSuccessfully();

        mockMvc.perform(post(URI_BANKSLIPS)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(bankslipDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.due_date", is(DateFormatter.format(bankslipDto.getDueDate()))))
                .andExpect(jsonPath("$.total_in_cents", is(CurrencyFormatter.format(bankslipDto.getTotalInCents()))))
                .andExpect(jsonPath("$.customer", is(bankslipDto.getCustomer())))
                .andExpect(jsonPath("$.status", is(bankslipDto.getStatus().name())));
    }

    @Test public void
    should_return_400_when_bankslip_not_provided() throws Exception {
        mockMvc.perform(post(URI_BANKSLIPS)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE));
    }

    @Test public void
    should_return_422_when_bankslip_is_invalid() throws Exception {
        sendBankslipInvalid(new BankslipDto());

        BankslipDto bankslipDtoWithoutDueDate = dataProvider.buildBankslipDtoForRegisterSuccessfully();
        bankslipDtoWithoutDueDate.setDueDate(null);
        sendBankslipInvalid(bankslipDtoWithoutDueDate);

        BankslipDto bankslipDtoWithoutTotalInCents = dataProvider.buildBankslipDtoForRegisterSuccessfully();
        bankslipDtoWithoutTotalInCents.setTotalInCents(null);
        sendBankslipInvalid(bankslipDtoWithoutTotalInCents);

        BankslipDto bankslipDtoWithoutCustomer = dataProvider.buildBankslipDtoForRegisterSuccessfully();
        bankslipDtoWithoutCustomer.setCustomer(null);
        sendBankslipInvalid(bankslipDtoWithoutCustomer);
    }

    @Test public void
    should_return_200_while_getting_the_bankslips() throws Exception {
        dataProvider.registerBankslipPending();
        dataProvider.registerBankslipPaid();
        dataProvider.registerBankslipPaidWithFine();
        Bankslip bankslipPaidWithFine = dataProvider.buildBankslipPaidWithFine();

        try {
            mockMvc.perform(get(URI_BANKSLIPS).contentType(APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[2].id", is(bankslipPaidWithFine.getId().toString())))
                    .andExpect(jsonPath("$[2].due_date", is(DateFormatter.format(bankslipPaidWithFine.getDueDate()))))
                    .andExpect(jsonPath("$[2].payment_date", is(DateFormatter.format(bankslipPaidWithFine.getPaymentDate()))))
                    .andExpect(jsonPath("$[2].total_in_cents", is(CurrencyFormatter.format(bankslipPaidWithFine.getTotalInCents()))))
                    .andExpect(jsonPath("$[2].customer", is(bankslipPaidWithFine.getCustomer())))
                    .andExpect(jsonPath("$[2].status", is(bankslipPaidWithFine.getStatus().name())));
        } finally {
            dataProvider.removeBankslipPending();
            dataProvider.removeBankslipPaid();
            dataProvider.removeBankslipPaidWithFine();
        }
    }

    @Test public void
    should_return_200_when_getting_bankslip_details() throws Exception {
        dataProvider.registerBankslipPaidWithFine();
        Bankslip bankslipPaidWithFine = dataProvider.buildBankslipPaidWithFine();
        BigDecimal fineCalculated = fineCalculatorService.calculate(bankslipPaidWithFine.getDueDate(), bankslipPaidWithFine.getTotalInCents());

        try {
            mockMvc.perform(get(URI_BANKSLIPS + "/" + bankslipPaidWithFine.getId()).contentType(APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(jsonPath("$.id", is(bankslipPaidWithFine.getId().toString())))
                    .andExpect(jsonPath("$.due_date", is(DateFormatter.format(bankslipPaidWithFine.getDueDate()))))
                    .andExpect(jsonPath("$.payment_date", is(DateFormatter.format(bankslipPaidWithFine.getPaymentDate()))))
                    .andExpect(jsonPath("$.total_in_cents", is(CurrencyFormatter.format(bankslipPaidWithFine.getTotalInCents()))))
                    .andExpect(jsonPath("$.fine", is(CurrencyFormatter.format(fineCalculated))))
                    .andExpect(jsonPath("$.customer", is(bankslipPaidWithFine.getCustomer())))
                    .andExpect(jsonPath("$.status", is(bankslipPaidWithFine.getStatus().name())));
        } finally {
            dataProvider.removeBankslipPaidWithFine();
        }
    }

    @Test public void
    should_return_404_while_getting_the_details_of_the_non_existent_bankslip() throws Exception {
        mockMvc.perform(get(URI_BANKSLIPS + ID_BANKSLIP_NON_EXISTENT)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE));
    }

    @Test public void
    should_return_204_when_pay_a_bankslip() throws Exception {
        dataProvider.registerBankslipPending();
        Bankslip bankslipPending = dataProvider.buildBankslipPending();

        try {
            BankslipPaymentDto bankslipPaymentDto = dataProvider.buildBankslipPaymentDtoForPaymentSuccessfully();

            mockMvc.perform(post(URI_BANKSLIPS + "/" + bankslipPending.getId() + URI_PAYMENTS).contentType(APPLICATION_JSON_UTF8_VALUE)
                    .content(new ObjectMapper().writeValueAsString(bankslipPaymentDto)))
                    .andExpect(status().isNoContent())
                    .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE));
        } finally {
            dataProvider.removeBankslipPending();
        }
    }

    @Test public void
    should_return_404_when_trying_to_pay_a_non_existent_bankslip() throws Exception {
        BankslipPaymentDto bankslipPaymentDto = dataProvider.buildBankslipPaymentDtoForPaymentSuccessfully();

        mockMvc.perform(post(URI_BANKSLIPS + "/" + ID_BANKSLIP_NON_EXISTENT + "/payments")
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(bankslipPaymentDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE));
    }

    @Test public void
    should_return_400_when_trying_to_pay_a_bankslip_without_payment() throws Exception {
        mockMvc.perform(post(URI_BANKSLIPS + "/" + ID_BANKSLIP_NON_EXISTENT + "/payments")
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE));
    }

    @Test public void
    should_return_422_when_bankslip_payment_is_invalid() throws Exception {
        dataProvider.registerBankslipPending();
        Bankslip bankslipPending = dataProvider.buildBankslipPending();

        try {
            sendPostInvalid("/" + bankslipPending.getId() + "/payments", new BankslipPaymentDto());
        } finally {
            dataProvider.removeBankslipPending();
        }
    }

    @Test public void
    should_return_204_when_cancel_a_bankslip() throws Exception {
        dataProvider.registerBankslipPaidWithFine();
        Bankslip bankslipPaidWithFine = dataProvider.buildBankslipPaidWithFine();

        try {
            mockMvc.perform(delete(URI_BANKSLIPS + "/" + bankslipPaidWithFine.getId())
                    .contentType(APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isNoContent())
                    .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE));
        } finally {
            dataProvider.removeBankslipPaidWithFine();
        }
    }

    @Test public void
    should_return_404_when_trying_to_cancel_a_non_existent_bankslip() throws Exception {
        mockMvc.perform(delete(URI_BANKSLIPS + "/" + ID_BANKSLIP_NON_EXISTENT)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE));
    }

    private void sendBankslipInvalid(BankslipDto bankslipDto) throws Exception {
        sendPostInvalid("/", bankslipDto);
    }

    private <T> void sendPostInvalid(String service, T dto) throws Exception {
        mockMvc.perform(post(URI_BANKSLIPS + service)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE));
    }
}
