package web.runner;

import web.repository.CategoryDAO;
import web.repository.CourseDAO;
import web.repository.InstructorDAO;
import web.repository.StudentDAO;
import web.model.entity.Category;
import web.model.entity.Course;
import web.model.entity.Instructor;
import web.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitDB implements CommandLineRunner {

    @Autowired CategoryDAO categoryDAO ;
    @Autowired CourseDAO courseDAO ;
    @Autowired StudentDAO studentDAO ;
    @Autowired InstructorDAO instDao;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- BootstrapDB ---START");
        LoadDB();
        System.out.println("--- BootstrapDB ---END");
    }

    void LoadDB(){
        // Category
        Category cat1 = new Category(1L, "Programming", "Learn leading Programming languages and Tools", null);
        Category cat2 = new Category(2L, "Music", "Excel yourself in playing Musical Instrument", null);

        // Instructor
        Instructor i1 = Instructor.builder().name("Manisha prasad").email("mp@ggg.com").build();
        Instructor i2 = Instructor.builder().name("lekhraj Dibkar").email("ld@ggg.com").build();
        Instructor i3 = Instructor.builder().name("Arush chauhan").email("ac@ggg.com").build();

        // Course
        List courses = new ArrayList<Course>();

        Course c1 = new Course(); c1.setId(1L);
            c1.setTitle("JavaScript MasterClass");
            c1.setDesc("Learn JavaScript with modern ES2021");
            c1.setInstructor(i1); c1.setCategory(cat1);
        courses.add(c1);

        Course c2 = new Course(); c2.setId(2L);
            c2.setTitle("CSS MasterClass");
            c2.setDesc("Learn How to style web page with CSS grid and flexbox in 2021");
            c2.setInstructor(i2);c2.setCategory(cat2);
        courses.add(c2);

        Course c3 = new Course(); c3.setId(3L);
            c3.setTitle("TypeScript MasterClass");
            c3.setDesc("Learn TypeSafety with Typescript in 2021");
            c3.setInstructor(i3); c3.setCategory(cat1);
        courses.add(c3);

        Student s1 = Student.builder()
                .courses(new ArrayList<Course>())
                .name("Shunkai").email("ssss@ggg.com").build();
                //s1.getCourses().add(c1);s1.getCourses().add(c2);s1.getCourses().add(c2);

        Student s2 = Student.builder()
                //.courses(courseDAO.findAll())
                .name("Poonam").email("pppp@ggg.com").build();


//NO need
        // categoryDAO.save(cat1);
        // categoryDAO.save(cat2);
        // instDao.save(i1);
        // instDao.save(i2);
        // instDao.save(i3);

        courseDAO.save(c1);
        courseDAO.save(c2);
        courseDAO.save(c3);

        s1.setCourses(courseDAO.findAll());
        List temp = new ArrayList<>(); temp.add(courseDAO.findById(1L).get()); s2.setCourses(temp);

//        s1.getCourses().addAll(courses);
//        s1.getCourses().add(c1);

        studentDAO.save(s1);
        studentDAO.save(s2);

    }
}

/*
    select * from course;
    select * from category;
    select *  from STUDENT_COURSE
    select *  from STUDENT;
    select *  from INSTRUCTOR;
 */


