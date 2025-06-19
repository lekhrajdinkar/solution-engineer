package web.services.impl;

import web.repository.InstructorDAO;
import web.model.entity.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.services.InstructorSrv;

import java.util.List;

@Component
public class InstructorSrvImpl implements InstructorSrv
{
    @Autowired
    InstructorDAO dao;


    @Override
    public List<Instructor> findAll() {
        return dao.findAll();
    }

    @Override
    public Instructor findById(Long aLong) {
        return dao.findById(aLong).get();
    }

    @Override
    public Long save(Instructor instructor) {
         dao.save(instructor);
        return instructor.getId();
    }

    @Override
    public Instructor update(Instructor instructor) {
        return dao.save(instructor);
    }

    @Override
    public Instructor delete(Instructor instructor) {
         dao.delete(instructor);
         return instructor;
    }
}
