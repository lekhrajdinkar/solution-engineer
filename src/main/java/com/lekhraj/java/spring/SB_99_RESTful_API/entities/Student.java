package com.lekhraj.java.spring.SB_99_RESTful_API.entities;

import com.lekhraj.java.spring.SB_99_RESTful_API.model.GenderEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Builder
@Table(name="STUDENT")
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@NamedQueries({
        @NamedQuery(name = "Student.findStudentByName", query = "select s from Student s where s.name=:sname"),
        @NamedQuery(name = "Student.findStudentTuple", query = "select s.name, s.birthDate as dob, s.gender as gender from Student s")
})
public class Student {
    @Id
    @GeneratedValue(generator = "myUUID")
    //@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @GenericGenerator(name = "myUUID"
            ,strategy = "com.lekhraj.java.spring.SB_99_RESTful_API.entities.CustomIdentifier"
            //,parameters = @Parameter(name = "prefix", value = "prod")
    )
    private UUID uuid;

    @Column(name="student_name", length=50, nullable=false, unique=false)
    private String name;

    @Transient
    private Integer age;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
}
