package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FxmlLoader {
    private Pane view;

    public Pane getPage(String fileName) {
        try {
            URL fileUrl = getClass().getResource("/Views/" + fileName + ".fxml");

            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML can't be loaded");
            }
            view = FXMLLoader.load(fileUrl);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(FxmlLoader.class.getName());
            logger.log(Level.INFO,"exception message");
        }
        return view;
    }
}
