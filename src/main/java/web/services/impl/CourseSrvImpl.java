package web.services.impl;

import web.repository.CourseDAO;
import web.model.dto.CourseDTO;
import web.model.mapper.CourseMapper;
import web.model.entity.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.services.CourseSrv;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseSrvImpl implements CourseSrv
{
    Logger logger = LoggerFactory.getLogger(CourseSrvImpl.class);

    @Autowired
    CourseDAO dao;

    @Override
    public List<Course> findAll() {
        List<Course> temp = dao.findAll();
        return temp.stream().map(x -> {
            logger.info(x.toString());
            x.setCategory(x.getCategory());
            return x;
        }).collect(Collectors.toList());
    }

    @Override
    public Course findById(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public Long save(Course course) {
         dao.save(course);
         return course.getId();
    }

    @Override
    public Course update(Course course) {
        save(course);
        return course;
    }

    @Override
    public Course delete(Course course) {
        dao.delete(course);
        return course;
    }

    // ------ DTO -----
    @Override
    public List<CourseDTO> findAllDto() {
        List<Course> temp = findAll();
        return temp.stream()
                .map((Course x )-> CourseMapper.model2Dto(x))
                .collect(Collectors.toList());
    }
}
