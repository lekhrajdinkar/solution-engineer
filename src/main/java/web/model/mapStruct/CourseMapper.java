package web.model.mapStruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.model.dto.CourseDTO;
import web.model.entity.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper
{
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(source = "instructor.id", target = "instructorId")
    @Mapping(source = "category.id", target = "categoryId")
    CourseDTO map(Course course);

    Course map(CourseDTO course);

    /*default Long mapInstructorId(Instructor instructor ){
        return instructor.getId()+100;
    }*/
}
