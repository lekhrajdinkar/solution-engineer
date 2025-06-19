package web.services.impl;

import web.repository.StudentDAO;
import web.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.services.StudentSrv;

import java.util.List;

@Component
public class StudentSrvImpl implements StudentSrv
{
    @Autowired
    StudentDAO dao;


    @Override
    public List<Student> findAll() {
        return dao.findAll();
    }

    @Override
    public Student findById(Long aLong) {
        return dao.findById(aLong).get();
    }

    @Override
    public Long save(Student student) {
        return dao.save(student).getId();
    }

    @Override
    public Student update(Student student) {
        return dao.save(student);
    }

    @Override
    public Student delete(Student student) {
         dao.delete(student);
        return student;
    }
}
