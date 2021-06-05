package Controllers;

import Client.Client;
import Raports.RaportFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CurrentUser;
import utils.Request;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class App implements Initializable, Observer {
    private final CurrentUser currentUser = CurrentUser.getInstance();
    @FXML
    private ToolBar toolbar;
    @FXML
    private MenuButton raports;
    @FXML
    private BorderPane mainFrame;

    public void loadPage(String page) {
        if (!"login".equals(page) && !currentUser.isLoggedIn())
            return;
        FxmlLoader loader = new FxmlLoader();
        Pane view = loader.getPage(page);
        mainFrame.setCenter(view);
    }

    @FXML
    public void handleButtonAnimals(ActionEvent actionEvent) {
        loadPage("Animals");
    }

    @FXML
    public void handleButtonConsultations(ActionEvent actionEvent) {
        loadPage("Consultations");
    }

    @FXML
    public void handleButtonStock(ActionEvent actionEvent) {
        loadPage("Stock");
    }

    @FXML
    public void handleButtonUsers(ActionEvent actionEvent) {
        loadPage("Users");

    }

    public String getPath(String filter) {
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setInitialDirectory(new File("src"));
        String path ;

        final Stage stage = (Stage) mainFrame.getScene().getWindow();
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile == null)
            return null;
        path = selectedFile.getAbsolutePath();
        return path;
    }

    @FXML
    public void generateText(ActionEvent actionEvent) throws IOException {
        String path = getPath("text");
        if (path == null)
            return;
        if (!path.endsWith(".txt"))
            path += ".txt";
        RaportFactory raportFactory = new RaportFactory();
        Request request4 = new Request("handleReport", "");
        request4.setSenderType("user");
        Client.Connection.sendMessageToServer(request4);
        raportFactory.createRaport("text", path).generateRaport();

    }

    @FXML
    public void generatePDF(ActionEvent actionEvent) throws IOException {
        String path = getPath("pdf");
        if (path == null)
            return;
        if (!path.endsWith(".pdf"))
            path += ".pdf";
        RaportFactory raportFactory = new RaportFactory();
        Request request4 = new Request("handleReport", "");
        request4.setSenderType("user");
        Client.Connection.sendMessageToServer(request4);
        raportFactory.createRaport("pdf", path).generateRaport();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toolbar.setVisible(false);
        loadPage("login");
        currentUser.addObserver(this);
    }

    private void setRights() {
        this.toolbar.setVisible(true);
        if (currentUser.isAdmin()) {
            this.toolbar.getItems().get(2).setVisible(true);
            this.toolbar.getItems().get(3).setVisible(true);
        }
    }

    public boolean getButtonRaportsVisible() {
        return this.toolbar.getItems().get(3).isVisible();
    }


    @Override
    public void update(Observable o, Object arg) {
        loadPage("Consultations");
        setRights();
    }

    public Object getName() {
        return null;
    }

    public Object getOwnerId() {
        return null;
    }
}
