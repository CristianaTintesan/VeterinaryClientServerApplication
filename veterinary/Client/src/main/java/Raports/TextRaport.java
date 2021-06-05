package Raports;

import model.Consulation;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextRaport implements Raport {
    private final String path;

    private static List<Consulation> consultations;

    public TextRaport(String path) {
        this.path = path;
    }

    @Override
    public void generateRaport() {

        try {
            File file = new File(path);
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            Logger logger = Logger.getLogger(TextRaport.class.getName());
            logger.log(Level.INFO,"exception message");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path), StandardOpenOption.APPEND)) {
            for (Consulation consultation : consultations) {
                writer.write("Consultation ID: " + consultation.getId() + "\n");
                writer.write("Date: " + consultation.getDate() + "\n");
                writer.write("Animal ID: " + consultation.getAnimalID() + "\n");
                writer.write("Animal name: " + consultation.getAnimal().getName() + "\n");
                writer.write("Owner id: " + consultation.getAnimal().getOwner().getId() + "\n");
                writer.write("Owner name: " + consultation.getAnimal().getOwner().getName() + "\n");
                writer.write("Consultation Status: " + consultation.getStatus() + "\n");
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.format(e.getMessage());
        }
    }

    public static void viewConsultation(List<Consulation> allConsultations) throws IOException {
        consultations = allConsultations;
    }
}
