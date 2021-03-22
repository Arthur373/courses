package am.aca.courses.services;

import am.aca.courses.entity.ApplicantEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service class for {@link ApplicantService}
 *
 * @author Arthur
 * @version 1.0
 */
public interface ApplicantService {

    List<ApplicantEntity> getAll();

    Optional<ApplicantEntity> getApplicantById(Long id);

    ApplicantEntity save(ApplicantEntity applicantEntity);

    ApplicantEntity update(ApplicantEntity applicantEntity);

    void deleteApplicantById(Long id);
}
