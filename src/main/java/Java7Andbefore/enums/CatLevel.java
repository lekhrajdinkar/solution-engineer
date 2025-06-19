package Java7Andbefore.enums;

import web.controller.serialize.MyEnumDeSerializer;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonDeserialize(using = MyEnumDeSerializer.class)
public enum CatLevel
{
    NOVICE(1,"NOVICE"), PRO(1,"PRO");

    private Integer id;
    private String name;

    @JsonValue
    // can have only one
    // @JsonValue indicates a single method that the library will use to serialize the entire instance.
    String getValue(){
        return name+"---suffix---JsonValue";
    }


    // @JsonValue // JsonMappingException
    String getValue2(){
        return name+"---suffix---JsonValue--2";
    }
}