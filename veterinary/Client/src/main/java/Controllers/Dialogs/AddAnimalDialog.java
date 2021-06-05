package Controllers.Dialogs;

import Client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.Request;
import java.io.IOException;


public class AddAnimalDialog {

    @FXML
    private TextField name;

    @FXML
    private TextField owner;

   /* @FXML
    private void saveButtonClicked(ActionEvent actionEvent) {
        if (owner.getText().equals("") || name.getText().equals(""))
            return;
        AnimalDAO animalDAO = new AnimalDAO();
        try {
            animalDAO.insert(new Animal(name.getText(), Integer.parseInt(owner.getText())));
        } catch (SQLException throwables) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.showAndWait();
            return;
        }
        closeWindow(actionEvent);
    }*/

    @FXML
    private void saveButtonClicked(ActionEvent actionEvent) throws IOException {
        String ownerId = owner.getText();
        String animalName = name.getText();

        Request request = new Request("handleAddAnimal", ownerId + " " + animalName);
        request.setSenderType("user");
        Client.Connection.sendMessageToServer(request);
        closeWindow(actionEvent);
    }

    @FXML
    private void cancelButtonClicked(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }


    private void closeWindow(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.fireEvent(
                new WindowEvent(
                        stage,
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }

    public String getOwnerId() {

        return owner.getText();
    }

    public String getName() {

        return name.getText();
    }
}
