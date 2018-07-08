package br.com.jhonatanserafim.bankslips.core.json;

import br.com.jhonatanserafim.bankslips.core.util.DateFormatter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider arg) throws IOException {
        gen.writeString(DateFormatter.format(value));
    }
}