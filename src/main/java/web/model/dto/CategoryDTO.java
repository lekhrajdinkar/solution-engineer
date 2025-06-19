package web.model.dto;

import web.controller.serialize.LocalDateDeSerializer;
import web.controller.serialize.LocalDateSerializer;
import Java7Andbefore.enums.CatLevel;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString


@JsonInclude(JsonInclude.Include.ALWAYS) //can also apply on feild level
@JsonPropertyOrder({ "title", "desc","id" }) //2. ordering
@JsonRootName(value="COURSE_ROOT")
// ß@JsonPropertyOrder(alphabetic=true) //2. ordering
// @JsonIgnoreProperties({ "id" })
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC) //overridee all
public class CategoryDTO {
    @JsonProperty
    Long id;

    @JsonProperty CatLevel level;

    @JsonProperty(value="title2")
    //@JsonRawValue ??
    String title;
    @JsonSetter //Used while De-serializing :   json to bean
    public void setTitle(String title){this.title = title+"---fromSetter---";}

    @JsonProperty
    String desc;

    @JsonProperty
    List<CourseDTO> courses;

    @JsonProperty
    //@JsonAnyGetter //1.Expand map
    Map<String, List<CourseDTO>> coursesMap; // <level, >

    @JsonProperty
    @JsonSerialize(using = LocalDateSerializer.class) // Custom
    @JsonDeserialize (using = LocalDateDeSerializer.class) // Custom
    LocalDate localDate = LocalDate.now();

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    Date testDate;

    @JsonIgnore
    String useless;

    @JsonIgnore
    private String privateProp;

    Optional<String> optionalString;

    @JsonProperty
    @JsonAnyGetter
    Map<String, String> testMap;

//    @Override
//    public String toString() {
//        return "CategoryDTO{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", desc='" + desc + '\'' +
//                ", courses=" + Optional.ofNullable(courses).stream().collect(Collectors.toList())+
//                '}';
//    }

    // 3. @JsonValue indicates a single method that the library will use to serialize the entire instance.
//    @JsonValue
//    String getValue(){
//        return String.valueOf(this.hashCode());
//    }
}


