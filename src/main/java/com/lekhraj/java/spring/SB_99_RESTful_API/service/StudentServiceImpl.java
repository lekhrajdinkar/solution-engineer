package com.lekhraj.java.spring.SB_99_RESTful_API.service;

import com.lekhraj.java.spring.SB_99_RESTful_API.custom.repository.StudentRepository;
import com.lekhraj.java.spring.SB_99_RESTful_API.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class StudentServiceImpl
{
    @Autowired
    StudentRepository repo;
    TransactionTemplate transactionTemplate;


    public Student getStudent(){
        this.transactionTemplate.setReadOnly(true); // Optimized Performance
        TransactionCallback<Student> cb = (s) -> repo.getStudent("Lekhraj-Dinkar");
        return this.transactionTemplate.execute(cb);
    }

}
