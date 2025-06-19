package web.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import jakarta.persistence.*;
//import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name="INSTRUCTOR")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String email;

    //Bidirectional mapping
    @OneToOne(mappedBy = "instructor")
    @JsonBackReference(value = "instructor_course_json")
    Course course;

}
