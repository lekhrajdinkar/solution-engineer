package web.controller;

import web.model.entity.Instructor;
import web.services.InstructorSrv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InstructorController
{

    Logger logger = LoggerFactory.getLogger(InstructorController.class);
    
    @Autowired
    InstructorSrv srv;

    //@Override
    @GetMapping("inst/find-all")
    public List<Instructor> findAll() {
        logger.debug("CourseController :: findAll");
        return srv.findAll();
    }

    //@Override
    @GetMapping("inst/findById/{id}")
    public Instructor findById(@PathVariable Long id) {
        logger.debug("CourseController :: findById");
        return srv.findById(id);
    }

    //@Override
    @PostMapping("inst/save")
    public Long save(@RequestBody Instructor model) {
        logger.debug("CourseController :: save");
        return srv.save(model);
    }
}
