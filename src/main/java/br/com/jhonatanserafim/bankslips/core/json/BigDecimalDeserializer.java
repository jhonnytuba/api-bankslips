package br.com.jhonatanserafim.bankslips.core.json;

import br.com.jhonatanserafim.bankslips.core.util.CurrencyFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return CurrencyFormatter.parse(parser.getText());
    }
}