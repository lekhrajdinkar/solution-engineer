package web.controller.serialize;

import Java7Andbefore.enums.CatLevel;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class MyEnumDeSerializer extends StdDeserializer<CatLevel> {

    protected MyEnumDeSerializer() {
        this(null);
    }
    protected MyEnumDeSerializer(Class vc) {
        super(vc);
    }

    @Override
    public CatLevel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
    throws IOException, JsonProcessingException
    {
        // Automatically happens. this is just Sample to understand more.
        switch(jsonParser.getText()){
            case "NOVICE" : return CatLevel.NOVICE ;
            case "PRO" : return CatLevel.PRO;
        }
        return null;
    }
}
