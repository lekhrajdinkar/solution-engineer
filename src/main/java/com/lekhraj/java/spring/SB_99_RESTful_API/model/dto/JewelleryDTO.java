package com.lekhraj.java.spring.SB_99_RESTful_API.model.dto;

import com.fasterxml.jackson.annotation.*;
//import com.fasterxml.jackson.annotation.JsonInject;
import com.lekhraj.java.spring.SB_99_RESTful_API.model.StatusEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

//@JsonIgnoreProperties({"password", "age"})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) //Default, priblic, private, protected,etc

public class JewelleryDTO
{
    // Bean/dto Validation
    // @NameCheckAnnotation
    @JsonProperty("jewelleryName") //@JsonIgnore
    @JsonAlias({"jewelleryNameAlias2", "jewelleryNameAlias1"})
    @NotBlank(message = "Name not given")
    String name;

    @JsonProperty("jewelleryId")
    @Min(value = 1, message ="Id missing") // Hibenate-validator
    int id;

    @JsonProperty("jewelleryPrice")
    //@Size(min = 10, max = 1000, message = "Must be between 100 and 1000")
    @Max(value = 1000, message = "maximum value is 1000.")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#0.00")
    double price;

    @DateTimeFormat //SB
    LocalDateTime createTime;

    //=========

    String derivedFeild;
    @JsonSetter("derive")
    void deriveSomevaluefromJsonFeild(String value){
        this.derivedFeild = value+"--"+value;
    }

    private Map<String, Object> unknownFeildMap = new HashMap<>();
    @JsonAnyGetter
    //On serialization, the properties from MAP will simply be added to JSON
    public Map<String, Object> getUnknownFeildMap() {
        return unknownFeildMap;
    }

    @JsonAnySetter
    //On deserialization, the "additional" properties from JSON will simply be added to this map.
    public void setProperty(String key, Object value) {
        unknownFeildMap.put(key, value);
    }
    // =========
    Category category;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    String nerverSetFeild;

    // @JsonValue  String wholeValue; // null
    // @JsonValue  String toString()

    @JsonInclude(JsonInclude.Include.ALWAYS)
    StatusEnum stockStatus;

    @JsonRawValue
    String rawdetailsJson = "{\"price\": 19.99, \"color\": \"red\"}";

    //@JsonKey - not right place
    // Apply inside MyKey : getKey(){}
    Map address = new HashMap<MyKey,String>(Map.of(
            MyKey.builder().prefix("pre-1").key("-city-").suffix("suffix-1").build(),"irvine",
            MyKey.builder().prefix("pre-2").key("-state-").suffix("suffix-2").build(),"CA"));

    // ===== Deserialize =====
    // @JsonDeserialize(using=abc.class)

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;

}

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreType
class Category{
    String type;
}

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class MyKey {
    String prefix;
    String key;
    String suffix;
    @JsonKey
    String getMyKey(){
        return "==="+this.prefix+this.key+this.suffix+"===";
    }
}
