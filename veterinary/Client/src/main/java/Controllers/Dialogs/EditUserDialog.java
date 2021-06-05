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


public class EditUserDialog {
    private int userID;

    @FXML
    private TextField name;


    public void setData(int userID, String name) {
        this.userID = userID;
        this.name.setText(name);
    }

    /*@FXML
    void saveButtonClicked(ActionEvent actionEvent) {
        if (name.getText().equals(""))
            return;
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.update(userID, Arrays.asList(name.getText()));
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

        String newName = name.getText();
        String idUser = String.valueOf(userID);
        Request request = new Request("handleUpdateUser", newName + " " + idUser);
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
