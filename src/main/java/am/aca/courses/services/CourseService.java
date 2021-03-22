package am.aca.courses.services;

import am.aca.courses.entity.CourseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service class for {@link CourseEntity}
 *
 * @author Arthur
 * @version 1.0
 */
public interface CourseService {

    List<CourseEntity> getAll();

    Optional<CourseEntity> getCourseByName(String name);

    Optional<CourseEntity> getCourseById(Long id);

    CourseEntity save(CourseEntity courseEntity);

    CourseEntity update(CourseEntity courseEntity);

    void deleteCourseByName(String name);
}
