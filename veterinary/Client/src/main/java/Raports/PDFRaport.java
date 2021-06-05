package Raports;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import model.Consulation;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PDFRaport implements Raport {
    private static List<Consulation> consultations;
    private final String path;

    //private static ObservableList<Consulation> data;

    //private List<Consulation> consultations;

    public PDFRaport(String path) {
        this.path = path;
    }

    @Override
    public void generateRaport() {
        Document document = new Document();
        //ConsultationDAO consultationDAO = new ConsultationDAO();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            document.add(new Paragraph("Consultations\n"));

            for (Consulation consultation : consultations) {
                document.add(new Paragraph("Consultation ID: " + consultation.getId() + "\n"));
                document.add(new Paragraph("Date: " + consultation.getDate() + "\n"));
                document.add(new Paragraph("Animal ID: " + consultation.getAnimalID() + "\n"));
                document.add(new Paragraph("Animal name: " + consultation.getAnimal().getName() + "\n"));
                document.add(new Paragraph("Owner id: " + consultation.getAnimal().getOwner().getId() + "\n"));
                document.add(new Paragraph("Owner name: " + consultation.getAnimal().getOwner().getName() + "\n"));
                document.add(new Paragraph("Consultation Status: " + consultation.getStatus() + "\n"));
                document.add(new Paragraph("\n"));
            }
            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            Logger logger = Logger.getLogger(PDFRaport.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }

    public static void viewConsultation(List<Consulation> allConsultations) throws IOException {
        consultations = allConsultations;
    }
}
