package am.aca.courses.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Comparator;

/**
 * Simple JavaBean domain object that represents a ApplicantEntity.
 *
 * @author Arthur
 * @version 1.0
 */
@Data
@Entity(name = "applicants")
public class ApplicantEntity implements Comparator<ApplicantEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @JsonProperty("phone_number")
    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    @ManyToOne(targetEntity = CourseEntity.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id",nullable = false)
    private CourseEntity course;


    @Override
    public int compare(ApplicantEntity applicant1, ApplicantEntity applicant2) {
        int nameCompare =  applicant1.getName().compareToIgnoreCase(applicant2.getName());
        if (nameCompare != 0) {
            return nameCompare;
        }
        int emailCompare = applicant1.getEmail().compareToIgnoreCase(applicant2.getEmail());
        if (emailCompare != 0) {
            return emailCompare;
        }
        return applicant1.getCourse().getName()
                .compareToIgnoreCase(applicant2.getCourse().getName());
      }
}
