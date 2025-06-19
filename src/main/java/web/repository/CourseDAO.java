package web.repository;

import web.model.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@Transactional
public interface CourseDAO extends JpaRepository<Course,Long> {
    //List<Course> findAll();

    @Query("SELECT c FROM COURSE c ORDER BY id")
    Page<Course> findAllWithPagination(Pageable pageable);

    Page<Course> findAll(Pageable pageable) ;

    @Query("SELECT c FROM COURSE c ORDER BY id")
    Slice<Course> findCoursePageSlice(Pageable pageable) ;

    @Query("SELECT c FROM COURSE c ORDER BY id")
    List<Course> findCoursePageList(Pageable pageable) ;

    List<Course> findAll(Sort sort);

    Optional<List<Course>> findByActiveTrue();
    Optional<List<Course>> findByActiveFalse();

    Optional<List<Course>> findByCategoryId(Long id);
    Page<Course> findByCategoryId(Long id,Pageable pageable);

    @Query("SELECT c FROM COURSE c WHERE c.category.id = ?1 and c.active = true")
    Optional<List<Course>> find1ByCategoryId(Long id);

    @Query("SELECT c FROM COURSE c WHERE c.category.id = :id and c.active = true")
    Optional<List<Course>> find2ByCategoryId(Long id);

    @Query("SELECT c FROM COURSE c WHERE c.category.id = :id1 and c.active = true")
    List<Course> find3ByCategoryId(@Param("id1") Long id);

    //======
    List<Course> findByInstructorIdEquals(Long id);
    List<Course> findByInstructorIdIsNot(Long id);
    List<Course> findByInstructorIdIsNull();

    @Query("SELECT c FROM COURSE c WHERE c.instructor.id = :id and c.active = true")
    List<Course> find1ByInstructorId(Long id);


    // Equality : above eg
    // compare: lessThanEqual, between, after, before
    // Similarity: below eg
    // And, or - For Complex Query use @Query
    // ...orderByXXXAsc

    List<Course> findByDesc(String desc);
    List<Course> findByDescContaining(String desc); //Not, IgnoreCase
    List<Course> findByDescEndingWith(String desc);
    List<Course> findByDescStartingWith(String desc);
    List<Course> findByDescLike(String desc);

    @Query("SELECT m FROM COURSE m WHERE m.desc LIKE %:desc%") // using Query
    List<Course> findByDescMyLike(String desc);



}

