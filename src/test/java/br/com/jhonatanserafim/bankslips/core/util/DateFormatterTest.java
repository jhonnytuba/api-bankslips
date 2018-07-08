package br.com.jhonatanserafim.bankslips.core.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class DateFormatterTest {

    @Test
    public void
    should_format_localdate_in_yyyy_mm_dd() {
        LocalDate date = LocalDate.of(2018, 7, 6);
        Assert.assertEquals("2018-07-06", DateFormatter.format(date));
    }

    @Test
    public void
    should_return_null_when_passed_the_null_parameter_to_format() {
        Assert.assertNull(DateFormatter.format(null));
    }

    @Test public void
    should_convert_yyyy_mm_dd_to_localdate() {
        String date = "2018-07-06";
        Assert.assertEquals(LocalDate.of(2018, 7, 6), DateFormatter.parse(date));
    }

    @Test
    public void
    should_return_null_when_passed_the_null_parameter_to_parse() {
        Assert.assertNull(DateFormatter.parse(null));
    }
}
