package am.aca.courses.services.pdf;

import am.aca.courses.entity.ApplicantEntity;
import am.aca.courses.services.ApplicantService;
import am.aca.courses.services.Applicants;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation of {@link Applicants} interface.
 *
 * @author Arthur
 * @version 1.0
 */
@Service
public class ApplicantsPDF implements Applicants {

    @Override
    public InputStream createPDF(ApplicantEntity applicant) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, bos);

        document.open();

        Paragraph p = new Paragraph("CERTIFICATE\n",
                FontFactory.getFont(FontFactory.TIMES, 12));
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.add(new Paragraph(" "));

        p.add(new Paragraph("INTRODUCTION TO COMPUTER SCIENCE\n",
                FontFactory.getFont(FontFactory.TIMES, 12)));

        String str = "Introduction to computer science and the study of algorithms; foundational ideas, computer organization, software concepts (e.g., networking, databases, security); programming concepts using Java.";
        p.add(new Paragraph(str,
                FontFactory.getFont(FontFactory.TIMES, 10)));
        p.add(new Paragraph(" "));

        Paragraph p1 = new Paragraph("Applicant name - " + applicant.getName(),
                FontFactory.getFont(FontFactory.TIMES, 12));
        p1.add(new Paragraph(" "));

        p1.add(new Paragraph("Teacher name - " + applicant.getCourse().getTeacherName(),
                FontFactory.getFont(FontFactory.TIMES, 12)));

        p1.add(new Paragraph("Course duration - "
                + ((applicant.getCourse().getEndDate().getTime() - applicant.getCourse().getStartDate().getTime()) / (1000L * 60 * 60 * 24 * 30)) + " month",
                FontFactory.getFont(FontFactory.TIMES, 12)));

        document.add(p);
        document.add(p1);

        document.close();
        return new ByteArrayInputStream(bos.toByteArray());
    }
}

