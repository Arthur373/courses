package am.aca.courses.controllers;


import am.aca.courses.entity.CourseEntity;
import am.aca.courses.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

/**
 * Rest Controller for Courses with CRUD operations.
 *
 * @author Arthur
 * @version 1.0
 */
@RestController
@RequestMapping("/rest")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseEntity>> getAllCourses() {
        List<CourseEntity> courses = this.courseService.getAll();
        try {
            return ResponseEntity
                    .ok(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/courses/_search")
    public ResponseEntity<CourseEntity> getCoursesById(@PathParam("name") String name) {
        Optional<CourseEntity> applicantEntity = this.courseService.getCourseByName(name);
        try {
            return ResponseEntity.ok().body(applicantEntity.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseEntity> createCourses(@RequestBody CourseEntity courseEntity) {
        CourseEntity course = courseService.save(courseEntity);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/courses/_search")
    public ResponseEntity<CourseEntity> updateCourses(@RequestBody CourseEntity courseEntity,
                                                   @PathParam("name") String name) {
        Optional<CourseEntity> course = courseService.getCourseByName(name);
        if (!course.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        courseEntity.setName(name);
        courseEntity = this.courseService.save(courseEntity);

        try {
            return ResponseEntity.ok().body(courseEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/courses/_search")
    public ResponseEntity deleteCourses(@PathParam("name") String name) {
        Optional<CourseEntity> course = courseService.getCourseByName(name);
        if (!course.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        this.courseService.deleteCourseByName(name);
        return ResponseEntity.ok().build();
    }
}
