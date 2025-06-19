package java8.streamProcessing;

import web.model.entity.Student;

import java.util.*;
import java.util.stream.Collectors;

import static util.Print.p;

public class MyCollecters3
{
    public static <HasSet> void main(String a[])
    {
         List<Student> students = new ArrayList<>();
            Student s1 = Student.builder().id(1L).name("Lekhraj").email("ld-1@g.com").build();
            Student s2 = Student.builder().id(2L).name("Lekhraj").email("ld-2@g.com").build();
            Student s3 = Student.builder().id(3L).name("Manisha").email("ld-3@g.com").build();
            students.add(s1); students.add(s2); students.add(s3);
         //------------ Java Collector ----------

         // ====== 1. Collector.mapping
        //1.1  java8 way
        List<String> r1=students
                .stream()
                .map(s->s.getName()+"--"+s.getEmail() )
                .collect(Collectors.toList());
        p(r1);

        //1.2 Jav 9 way
        r1=students
                .stream()
                .collect(Collectors.mapping(
                        s->s.getName()+"--"+s.getEmail(),
                        Collectors.toList()
                ));
        p(r1);

        //1.3 group by - lek and other. grouping last
        Map<String,List<String>> r2=students
                .stream()
                .collect(Collectors.mapping(
                        s->s.getName()+"--"+s.getEmail(),
                        Collectors.groupingBy(x->x.contains("Lekhraj")?"Lek":"others")
                ));
        p(r2);

        // 1.4 group by - lek and other. grouping first
        r2 = students
                .stream()
                .collect(Collectors.groupingBy(
                        s->s.getName().equals("Lekhraj") ? "lek" : "Others",
                        Collectors.mapping(
                                s->s.getName()+"--"+s.getEmail(),
                                Collectors.toList())
                ));
        p(r2);

    }
}


