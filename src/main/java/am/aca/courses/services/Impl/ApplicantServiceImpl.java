package am.aca.courses.services.Impl;

import am.aca.courses.entity.ApplicantEntity;
import am.aca.courses.repositories.ApplicantRepository;
import am.aca.courses.services.ApplicantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link ApplicantService} interface.
 *
 * @author Arthur
 * @version 1.0
 */
@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Override
    public List<ApplicantEntity> getAll() {
        return applicantRepository.findAll();
    }

    @Override
    public Optional<ApplicantEntity> getApplicantById(Long id) {
        return applicantRepository.findById(id);
    }

    @Override
    public ApplicantEntity save(ApplicantEntity applicantEntity) {
        return applicantRepository.save(applicantEntity);
    }

    @Override
    public ApplicantEntity update(ApplicantEntity applicantEntity) {
        return applicantRepository.save(applicantEntity);
    }

    @Override
    public void deleteApplicantById(Long id) {
        applicantRepository.deleteById(id);
    }
}
