package Controllers.Dialogs;

import Client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.Request;

import java.io.IOException;

public class AddUserDialog {

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    /*@FXML
    private void saveButtonClicked(ActionEvent actionEvent) {
        if (name.getText().equals("") || password.getText().equals(""))
            return;
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.insert(new User(name.getText(), password.getText()));
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

        String username=name.getText();
        String pass=password.getText();

        Request request = new Request("handleAddUser", username + " " + pass);
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

    public String getName(){
        return name.getText();
    }

    public String getPass(){
        return password.getText();
    }


}
