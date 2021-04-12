package am.aca.courses.services;

import am.aca.courses.entity.ApplicantEntity;

import java.io.IOException;
import java.io.InputStream;


public interface ApplicantsPDF {

    InputStream createPDF(ApplicantEntity applicant) throws IOException;
}
