package web.model.entity;

import jakarta.persistence.*;
//import javax.persistence.*;

@Entity(name="LESSON")
public class Lesson {
    @Id Long id;
    String title;
    String desc;
    String url;
}
