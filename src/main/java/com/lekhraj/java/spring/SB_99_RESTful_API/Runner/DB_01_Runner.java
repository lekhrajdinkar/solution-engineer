package com.lekhraj.java.spring.SB_99_RESTful_API.Runner;

import com.lekhraj.java.spring.SB_99_RESTful_API.custom.repository.StudentRepository;
import com.lekhraj.java.spring.util.Print;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
public class DB_01_Runner implements CommandLineRunner {
    @Autowired
    StudentRepository StudentRepo;
    @Override
    public void run(String... args) throws Exception
    {
        Print.print("DB_01_Runner" , "01-StudentRepo.AddStudent()",
                StudentRepo.addStudent("Lekhraj-Dinkar"),
                StudentRepo.addStudent("Manisha-Prasad"));

        Print.print("DB_01_Runner" , "02-StudentRepo.getStudent()", StudentRepo.getStudent("Lekhraj-Dinkar") );

        Print.print("DB_01_Runner" , "03-StudentRepo.getTuple()");
        List<Tuple> studentTuples  = StudentRepo.getTuple();
        for(Tuple t : studentTuples){
           log.info(" tuple - {}, name-{}, bod-{}, gender-{}",t, t.get(0), t.get(1), t.get("gender"));
            // "select s.name, s.birthDate as dob, s.gender as gender from Student s"
            // notice alias : s.gender as gender
        }
    }
}
