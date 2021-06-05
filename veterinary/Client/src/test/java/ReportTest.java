import Raports.PDFRaport;
import Raports.Raport;
import Raports.RaportFactory;
import Raports.TextRaport;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ReportTest {

    @Test
    public void createPDFRaport() {
        RaportFactory raportFactory = new RaportFactory();
        Raport raport = raportFactory.createRaport("PdF", "path");
        boolean isPdf = raport instanceof PDFRaport;
        assertTrue(isPdf);
    }


    @Test
    public void createTextRaport() {
        RaportFactory raportFactory = new RaportFactory();
        Raport raport = raportFactory.createRaport("text", "path");
        boolean isTxt = raport instanceof TextRaport;
        assertTrue(isTxt);
    }

}
