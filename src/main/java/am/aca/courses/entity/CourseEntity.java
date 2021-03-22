package am.aca.courses.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Simple JavaBean domain object that represents a CourseEntity.
 *
 * @author Arthur
 * @version 1.0
 */
@Data
@Entity(name = "courses")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "teacher_name")
    private String teacherName;

    private String description;

    @Column(name = "start_date")
    private Date startDate = new Date(System.currentTimeMillis() + 120 * 1000);

    @Column(name = "end_date")
    private Date endDate = new Date(startDate.getTime() + 7200 * 1000);

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", description='" + description;
    }
}
