package web.model.mapStruct;

import web.model.dto.CourseDTO;
import web.model.entity.Category;
import web.model.entity.Course;
import web.model.entity.Instructor;

import static util.Print.p;

public class Runner {
    public static void main(String[] args)
    {
        CourseMapper mapper = CourseMapper.INSTANCE;
        // entity ( with dependencies ) >> dto
        Instructor i1 = Instructor.builder().id(1L).name("istr-1").build();
        Category cat1 = Category.builder().id(1L).desc("category-1").build();
        Course c1_entity = Course.builder().id(1L).desc("c1-desc").title("c1").instructor(i1).category(cat1).build();
        CourseDTO c1_dto =mapper.map(c1_entity);
        p("dto", c1_dto, "entity", c1_entity);

        CourseDTO c2_dto = CourseDTO.builder().id(2L).desc("c2-desc").title("c2").categoryId(2L).instructorId(2L).build();
        Course c2_entity =mapper.map(c2_dto);
        p("dto", c2_dto, "entity", c2_entity);
    }
}
