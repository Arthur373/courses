package am.aca.courses.repositories;

import am.aca.courses.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Long> {

    Optional<CourseEntity> findCourseEntityByName(String name);

    void deleteCourseEntityByName(String name);
}
