package am.aca.courses.services.Impl;

import am.aca.courses.entity.CourseEntity;
import am.aca.courses.repositories.CourseRepository;
import am.aca.courses.services.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link CourseService} interface.
 *
 * @author Arthur
 * @version 1.0
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseEntity> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<CourseEntity> getCourseByName(String name) {
        return courseRepository.findCourseEntityByName(name);
    }

    @Override
    public Optional<CourseEntity> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public CourseEntity save(CourseEntity courseEntity) {
        return courseRepository.save(courseEntity);
    }

    @Override
    public CourseEntity update(CourseEntity courseEntity) {
        return courseRepository.save(courseEntity);
    }

    @Override
    public void deleteCourseByName(String name) {
        courseRepository.deleteCourseEntityByName(name);
    }
}
