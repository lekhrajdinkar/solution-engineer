package web.services;

import web.model.dto.CourseDTO;
import web.model.entity.Course;

import java.util.List;

public interface CourseSrv  extends BaseSrv<Course, Long>{
    public List<CourseDTO> findAllDto();
}
