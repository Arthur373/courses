package am.aca.courses.controllers;


import am.aca.courses.entity.ApplicantEntity;
import am.aca.courses.entity.Status;
import am.aca.courses.services.ApplicantService;
import am.aca.courses.services.ApplicantsPDF;
import am.aca.courses.services.CourseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Rest Controller for Applicants with CRUD operations.
 *
 * @author Arthur
 * @version 1.0
 */
@RestController
@RequestMapping("/rest")
public class ApplicantController {

    private final ApplicantService applicantService;
    private final CourseService courseService;
    private final ApplicantsPDF applicantsPDF;

    private static final String ZIP_MIME_TYPE = "application/zip";

    public ApplicantController(ApplicantService applicantService, CourseService courseService, ApplicantsPDF applicantsPDF) {
        this.applicantService = applicantService;
        this.courseService = courseService;
        this.applicantsPDF = applicantsPDF;
    }

    @GetMapping("/applicants")
    public ResponseEntity<List<ApplicantEntity>> getAllApplicants() {
        try {
            List<ApplicantEntity> applicantEntities = this.applicantService.getAll();
            applicantEntities.sort(new ApplicantEntity());
            return ResponseEntity
                    .ok(applicantEntities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * pdf
     */
    @GetMapping(value = "/applicants/zip/_download", produces = ZIP_MIME_TYPE)
    public ResponseEntity<StreamingResponseBody> downloadUsersAsExcel() {
        try {
            List<ApplicantEntity> applicantEntities = this.applicantService.getAll();
            Map<String, InputStream> pdfs = new HashMap<>();
            for (ApplicantEntity applicantEntity : applicantEntities) {
                if(applicantEntity.getStatus() == Status.COMPLETED) {
                    InputStream inputStream = this.applicantsPDF.createPDF(applicantEntity);
                    pdfs.put(applicantEntity.getName() + ".pdf", inputStream);
                }
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "certificates.zip" + "\"")
                    .body(out -> {
                        var zippedOut = new ZipOutputStream(out);

                        // create a map to add pdf files to be zipped
                        for (Map.Entry<String, InputStream> pdf : pdfs.entrySet()) {
                            ZipEntry e = new ZipEntry(pdf.getKey());
                            zippedOut.putNextEntry(e);
                            StreamUtils.copy(pdf.getValue(), zippedOut);
                        }
                        zippedOut.closeEntry();
                        zippedOut.finish();
                        zippedOut.close();
                    });
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/applicants/{id}")
    public ResponseEntity<ApplicantEntity> getApplicantsById(@PathVariable("id") Long id) {
        try {
            Optional<ApplicantEntity> applicantEntity = this.applicantService.getApplicantById(id);
            ApplicantEntity applicant = null;
            // set Status
            if (applicantEntity.isPresent()) {
                applicant = applicantEntity.get();
                this.getStatus(applicant);
            }
            return ResponseEntity.ok().body(applicant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/applicants")
    public ResponseEntity<ApplicantEntity> createApplicants(@RequestBody ApplicantEntity applicantEntity) {
        try {
            applicantEntity.setCourse(this.courseService.getCourseById(2L).get());
            //set Status
            if (applicantEntity.getCourse() != null && applicantEntity.getCourse().getStartDate().after(new Date())) {
                applicantEntity.setStatus(Status.ON_HOLD);
            } else {
                applicantEntity.setStatus(Status.IN_PROGRESS);
            }
            ApplicantEntity applicant = applicantService.save(applicantEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(applicant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/applicants/{id}")
    public ResponseEntity<ApplicantEntity> updateApplicants(@RequestBody ApplicantEntity applicantEntity,
                                                            @PathVariable("id") Long id) {
        Optional<ApplicantEntity> applicant = applicantService.getApplicantById(id);
        if (!applicant.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        applicantEntity.setId(id);
        applicantEntity.setStatus(applicant.get().getStatus());
        applicantEntity.setCourse(applicant.get().getCourse());
        applicantEntity = this.applicantService.update(applicantEntity);
        try {
            return ResponseEntity.ok().body(applicantEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/applicants/{id}")
    public ResponseEntity deleteApplicants(@PathVariable("id") Long id) {
        this.applicantService.deleteApplicantById(id);
        return ResponseEntity.ok().build();
    }


    private void getStatus(ApplicantEntity applicant) {
        if (applicant.getCourse().getStartDate().before(new Date())
                && applicant.getCourse().getEndDate().after(new Date())) {
            applicant.setStatus(Status.IN_PROGRESS);
            applicantService.update(applicant);
        } else if (applicant.getCourse().getEndDate().before(new Date())) {
            applicant.setStatus(Status.COMPLETED);
            applicantService.update(applicant);
        }
    }
}
