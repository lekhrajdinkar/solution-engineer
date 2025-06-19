package web.controller.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;


public class LocalDateSerializer extends StdSerializer<LocalDate> {

    public LocalDateSerializer(Class<LocalDate> t) {
        super(t);
    }
    public LocalDateSerializer() {
        this(null);
    }

    @Override
    public void serialize(LocalDate localDate, JsonGenerator generator, SerializerProvider serializerProvider)
            throws IOException {

        //generator.writeString(String.valueOf(localDate.getYear()));

        generator.writeStartObject();
            generator.writeFieldName("year");
            generator.writeString(String.valueOf(localDate.getYear()));
            generator.writeFieldName("month");
            generator.writeString(String.valueOf(localDate.getMonth()));
            generator.writeFieldName("day");
            generator.writeNumber(String.valueOf(localDate.getDayOfMonth()));
//            generator.writeStartObject();
//                generator.writeFieldName("year");
//                generator.writeString(String.valueOf(localDate.getYear()));
//            generator.writeEndObject();

        generator.writeEndObject();
    }
}
