package Raports;

public class RaportFactory {
    public Raport createRaport(String raportType, String path) {
        if (raportType == null)
            return null;
        if ("text".equalsIgnoreCase(raportType))
            return new TextRaport(path);
        if ("pdf".equalsIgnoreCase(raportType))
            return new PDFRaport(path);
        return null;
    }
}
