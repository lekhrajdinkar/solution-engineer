package com.lekhraj.java.spring.SB_99_RESTful_API.custom.repository;

import com.lekhraj.java.spring.SB_99_RESTful_API.entities.Student;
import com.lekhraj.java.spring.SB_99_RESTful_API.model.GenderEnum;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class StudentRepository {
    @Autowired  EntityManagerFactory sessionFactory;

    public Student getStudent(String name){
        EntityManager em = sessionFactory.createEntityManager();

        TypedQuery<Student> q = em.createQuery("select s from Student s where s.name=:sname", Student.class);
        q.setParameter("sname",name);

        TypedQuery<Student> nq = em.createNamedQuery("Student.findStudentByName", Student.class);
        nq.setParameter("sname",name);

        Student result =  nq.getSingleResult(); //getResultList()/Set()
        em.close();
        return result;
    }

    public Student addStudent(String name){
        Student s = Student.builder()
                .age(32)
                .uuid(UUID.randomUUID())
                .gender(GenderEnum.FEMALE)
                .birthDate(LocalDate.of(1991, 5, 18))
                .name(name)
                .build();
        EntityManager em = sessionFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(s);
        transaction.commit();
        return s;
    }

    public List<Tuple> getTuple(){
        EntityManager em = sessionFactory.createEntityManager();
        // Tuple === Object[]
        TypedQuery<Tuple> nq = em.createNamedQuery("Student.findStudentTuple", Tuple.class);
        List<Tuple> result =  nq.getResultList();
        em.close();
        return result;
    }
}
