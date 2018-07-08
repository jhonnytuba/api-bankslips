package br.com.jhonatanserafim.bankslips.core.json;

import br.com.jhonatanserafim.bankslips.core.util.CurrencyFormatter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider arg) throws IOException {
        gen.writeString(CurrencyFormatter.format(value));
    }
}