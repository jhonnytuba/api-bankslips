package br.com.jhonatanserafim.bankslips.core.util;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CurrencyFormatterTest {

    @Test
    public void
    should_format_bigdecimal_in_value_of_two_decimal_places_without_separators() {
        BigDecimal currency = BigDecimal.valueOf(19.216);
        Assert.assertEquals("1921", CurrencyFormatter.format(currency));
    }

    @Test
    public void
    should_return_null_when_passed_the_null_parameter_to_format() {
        Assert.assertNull(CurrencyFormatter.format(null));
    }

    @Test public void
    should_convert_value_to_two_decimal_places_with_no_separators_in_bigdecimal() {
        String currency = "12345";
        Assert.assertEquals(BigDecimal.valueOf(123.45), CurrencyFormatter.parse(currency));
    }

    @Test
    public void
    should_return_null_when_passed_the_null_parameter_to_parse() {
        Assert.assertNull(CurrencyFormatter.parse(null));
    }
}
