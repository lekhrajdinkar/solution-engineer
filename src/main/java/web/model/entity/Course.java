package web.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.*;
//import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
//@EntityListeners(AuditListener.class)
@Entity(name="COURSE")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    String title;
    String desc;

    @Column
    //1. @ColumnDefault(value="TRUE") //2. @PrePersist()
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name="CAT_ID")
    @JsonManagedReference(value = "category_course_json")
    private Category category;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="INST_ID")
    @JsonManagedReference(value = "instructor_course_json")
    private Instructor instructor;

    @ManyToMany(mappedBy = "courses")
    //@JsonManagedReference
    @JsonBackReference(value = "student_course_json")
    private List<Student> students;


    // @OneToMany List<Section> sections;

//    @Column(
//            name = "created_on"
//            //,columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP"
//    )
//    //@Generated(GenerationTime.INSERT)
//    private LocalDateTime createdOn;

    //Enver - Auditing Framework
    @CreatedDate @Column @Temporal(TemporalType.DATE) private Date createdDate = new Date();
    @LastModifiedDate @Column @Temporal(TemporalType.DATE) private Date modifiedDate= new Date();;
    @CreatedBy private String createdBy = "SB";
    @LastModifiedBy private String modifiedBy = "SB";

//    //Life Cycle callback
//    @PrePersist
//    public void onPrePersist() {  }
//
//    @PreUpdate
//    public void onPreUpdate() { }
//
//    @PreRemove
//    public void onPreRemove() {  }
}
