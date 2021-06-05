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


public class EditAnimalDialog {
    private int animalID;
    @FXML
    private TextField name;
    @FXML
    private TextField id;

    public void setData(int animalID, int ownerID, String name) {
        this.animalID = animalID;
        this.id.setText(Integer.toString(ownerID));
        this.name.setText(name);
    }

    /*@FXML
    void saveButtonClicked(ActionEvent actionEvent) {
        if (id.getText().equals("") || name.getText().equals(""))
            return;
        AnimalDAO animalDAO = new AnimalDAO();
        try {
            animalDAO.update(animalID, Arrays.asList(id.getText(), name.getText()));

        } catch (SQLException throwables) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.showAndWait();
            return;
        }
        closeWindow(actionEvent);
    }*/

    @FXML
    void saveButtonClicked(ActionEvent actionEvent) throws IOException {

        String animalName = name.getText();
        String ownerId = id.getText();
        String  animalId = String.valueOf(animalID);

        Request request = new Request("handleUpdateAnimal", ownerId + " " + animalName + " " + animalId);
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
}
