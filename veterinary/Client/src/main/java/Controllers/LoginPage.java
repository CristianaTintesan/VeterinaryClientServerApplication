package Controllers;

import Client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.*;
import utils.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginPage {

    private static Login login;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;

    public static User user;

    public void setLogin(Login login) {
        this.login = login;
    }

    /*@FXML
    public void login2222(ActionEvent actionEvent) {
        Window owner = submitButton.getScene().getWindow();
        if (usernameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login error");
            alert.setContentText("Please enter your username");
            alert.initOwner(owner);
            alert.show();
            return;
        }
        if (passwordField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login error");
            alert.setContentText("Please enter your password");
            alert.initOwner(owner);
            alert.show();
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();
        login = new Login();
        try {
            if (login.validate(username, password)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Login Successful");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login error");
                alert.setContentText("Wrong username/password");
                alert.initOwner(owner);
                alert.show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/

    @FXML
    public void login(){
       try {

            String username = usernameField.getText();
            String password = passwordField.getText();

            login = new Login();
            login.validate(username, password);
            Request request = new Request("handleUserLogin", username + " " + password);
            request.setSenderType("user");
            Client.Connection.sendMessageToServer(request);

       } catch (IOException | SQLException ex) {
           Logger logger = Logger.getLogger(LoginPage.class.getName());
           logger.log(Level.INFO,"exception message");
       }
    }

    public static void setCurrentUser(User user2) throws IOException {
        user=user2;
    }



}
