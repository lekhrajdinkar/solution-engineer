package web.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;
//import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name="STUDENT")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String email;

    @ManyToMany //(cascade = CascadeType.ALL)
    @JoinTable(
            name = "STUDENT_COURSE",
            joinColumns = { @JoinColumn(name = "COURSE_ID") },
            inverseJoinColumns = { @JoinColumn(name = "STUDENT_ID")}
    )
    //@JsonBackReference
    @JsonManagedReference(value = "student_course_json")
    List<Course> courses;

}
