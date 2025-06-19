package web.controller;

import web.exception.MyException;
import web.repository.CourseDAO;
import web.model.dto.CourseDTO;
import web.model.mapper.CourseMapper;
import web.model.entity.Course;
import web.services.CategorySrv;
import web.services.CourseSrv;
import web.services.InstructorSrv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CourseController 
{
    Logger logger = LoggerFactory.getLogger(CourseController.class);
    
    @Autowired CourseSrv srv;
    @Autowired CategorySrv categorySrv;
    @Autowired InstructorSrv instSrv;

    //@Override
    @GetMapping("course/find-all")
    private List<Course> findAll() {
        logger.debug("CourseController :: findAll");
        List<Course>  res = srv.findAll();
        res.stream().forEach(System.out::print);
        return srv.findAll();
    }

    @GetMapping("course/find-all-dto")
    private List<CourseDTO> findAllDto() {
        logger.debug("CourseController :: findAllDto");
        return srv.findAllDto();
    }

    //@Override
    @GetMapping("course/findById")
    private Course findById(Long id) {
        logger.debug("CourseController :: findById");
        return srv.findById(id);
    }

    //@Override
    @PostMapping("course/save")
    private Long save(@RequestBody CourseDTO dto) {
        logger.debug("CourseController :: save");

            Course c = CourseMapper.dto2Model(dto); //manual mapper, Todo: use mapStruct
            c.setCategory(categorySrv.findById(dto.getCategoryId())); // loading Category Entity manually from id.
            c.setInstructor(instSrv.findById(dto.getInstructorId()));

        return srv.save(c);
    }

    // ============ @Query ===========

    @Autowired CourseDAO dao;

    @GetMapping(
            value="course/q-find-by-cat",
            produces="application/v1+json"
    )
    private List<CourseDTO> QFindByCategory() {
        logger.debug("CourseController :: QFindByCategory");
        //List<Course>  res =  dao.find2ByCategoryId(1L).get();
        List<Course>  res =  dao.find3ByCategoryId(1L);
        return res.stream()
                .map((Course x )-> CourseMapper.model2Dto(x))
                .collect(Collectors.toList());
    }

    @GetMapping("course/q-find-by-inst")
    private List<CourseDTO> QFindByInstructor() {
        logger.debug("CourseController :: QFindByInstructor");
        //List<Course>  res =  dao.find2ByCategoryId(1L).get();
        List<Course>  res =  dao.find1ByInstructorId(1L);
        return res.stream()
                .map((Course x )-> CourseMapper.model2Dto(x))
                .collect(Collectors.toList());
    }

    @GetMapping("course/q-find-All-page/{page}/{size}")
    private List<CourseDTO> QFindAllPage(@PathVariable int page, @PathVariable int size) {
        logger.debug("CourseController :: QFindAllPage");
        Page<Course> res = dao.findAllWithPagination( PageRequest.of(page, size, Sort.by("title").descending().and(Sort.by("desc"))));
        return res
                .getContent()
                .stream()
                .map((Course x )-> CourseMapper.model2Dto(x))
                .collect(Collectors.toList());
    }

    // ============ Exception ===========
    @RequestMapping( method = RequestMethod.GET, value = "/exp")
    private ResponseEntity<String> testException()
    {
        return ResponseEntity.badRequest().body("Exception testing - body");
    }

    @RequestMapping( method = RequestMethod.GET, value = "/exp/control-advise")
    private ResponseEntity<String> testException2()
    throws Exception
    {
        throw new MyException("Exception testing - body");
    }

    @RequestMapping( method = RequestMethod.GET, value = "/exp/rs-exp")
    @ResponseStatus(value = HttpStatus.BAD_REQUEST) //status 1 - Default response
    private ResponseEntity<String> testException3()
    throws ResponseStatusException
    {
        throw new  ResponseStatusException(HttpStatus.FORBIDDEN, " Spring 5 - Response Status Exception"); //status 2 - forbidden
    }
}
