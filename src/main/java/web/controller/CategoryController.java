package web.controller;

import web.model.entity.Category;
import web.services.CategorySrv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController //implements CategoryApi
{

    Logger logger = LoggerFactory.getLogger(CategoryController.class);
    
    @Autowired CategorySrv srv;

    @GetMapping("/hello")  public String hello() {
        logger.debug("CategoryController :: hello");
        return "Hello";
    }

    //@Override
    @GetMapping("category/find-all")
    public List<Category> findAll() {
        logger.debug("CategoryController :: findAll");
        return srv.findAll();
    }

    //@Override
    @GetMapping("category/findById")
    public Category findById(Long id) {
        logger.debug("CategoryController :: findById");
        return srv.findById(id);
    }

    //@Override
    @PostMapping(
            value = "category/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Long save(@RequestBody  Category category) {
        logger.debug("CategoryController :: save");
        return srv.save(category);
    }
}
