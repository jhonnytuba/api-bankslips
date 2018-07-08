package br.com.jhonatanserafim.bankslips.api.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FineCalculatorServiceImplTest {

    @Spy
    @Autowired
    private FineCalculatorServiceImpl fineCalculatorService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void
    should_calculate_interest_free_value() {
        BigDecimal fineCalculated = fineCalculatorService.calculate(LocalDate.now(), BigDecimal.ONE);
        assertEquals(BigDecimal.ZERO, fineCalculated);
    }

    @Test public void
    should_calculate_fine_until_10_days() {
        LocalDate dueDate = LocalDate.of(2018, 5, 10);
        LocalDate paymentDate = LocalDate.of(2018, 5, 13);
        BigDecimal totalInCents = BigDecimal.valueOf(990d);
        BigDecimal fineExpected = BigDecimal.valueOf(14.85d);

        calculateFine(dueDate, paymentDate, totalInCents, fineExpected);
    }

    @Test public void
    should_calculate_fine_after_10_days() {
        LocalDate dueDate = LocalDate.of(2018, 5, 10);
        LocalDate paymentDate = LocalDate.of(2018, 5, 24);
        BigDecimal totalInCents = BigDecimal.valueOf(990d);
        BigDecimal fineExpected = BigDecimal.valueOf(138.6d);

        calculateFine(dueDate, paymentDate, totalInCents, fineExpected);
    }

    private void calculateFine(LocalDate dueDate, LocalDate paymentDate, BigDecimal totalInCents, BigDecimal fineExpected) {
        when(fineCalculatorService.getCurrentDate()).thenReturn(paymentDate);
        BigDecimal fineCalculated = fineCalculatorService.calculate(dueDate, totalInCents);
        assertEquals(fineExpected.doubleValue(), fineCalculated.doubleValue(), 0.001);
    }
}
