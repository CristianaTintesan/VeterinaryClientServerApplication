package Controllers;

import Client.Client;
import Controllers.Dialogs.EditUserDialog;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CurrentUser;
import model.User;
import utils.Request;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersPage implements Initializable {

    private final CurrentUser currentUser = CurrentUser.getInstance();
    //private final UserDAO userDAO = new UserDAO();
    private static ObservableList<User> data;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, String> type;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableView.setItems(data);

        if (currentUser.isAdmin()) {
            tableView.setRowFactory(
                    tableView -> {
                        final TableRow<User> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem editItem = new MenuItem("Edit");
                        editItem.setOnAction(event -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass()
                                        .getResource("/Views/Dialogs/editUserDialog.fxml"));
                                Parent root = loader.load();
                                EditUserDialog editPopup = loader.getController();
                                editPopup.setData(row.getItem().getId(), row.getItem()
                                        .getName());
                                Stage stage = new Stage();
                                stage.setTitle("Edit user");
                                stage.setScene(new Scene(root));
                                stage.show();
                                stage.setOnCloseRequest(event1 -> {
                                    //data = FXCollections.observableArrayList(userDAO.getAll());
                                    tableView.setItems(data);
                                });
                            } catch (IOException exception) {
                                Logger logger = Logger.getLogger(UsersPage.class.getName());
                                logger.log(Level.INFO,"exception message");
                            }
                        });
                        MenuItem removeItem = new MenuItem("Delete");
                        removeItem.setOnAction(event -> {
                            tableView.getItems().remove(row.getItem());
                            int idUser = row.getItem().getId();
                            try {
                                deleteUser(String.valueOf(idUser));
                            } catch (IOException e) {
                                Logger logger = Logger.getLogger(UsersPage.class.getName());
                                logger.log(Level.INFO,"exception message");
                            }
                            //userDAO.delete(row.getItem().getId());
                        });
                        rowMenu.getItems().addAll(editItem, removeItem);
                        row.contextMenuProperty().bind(
                                Bindings.when(row.emptyProperty())
                                        .then((ContextMenu) null)
                                        .otherwise(rowMenu));
                        return row;
                    });
        }
    }

    public void deleteUser(String idUser) throws IOException {

        Request request = new Request("handleDeleteUser", idUser);
        request.setSenderType("user");
        Client.Connection.sendMessageToServer(request);
    }


    public static void viewUsers(List<User> allUsers) throws IOException {
        data = FXCollections.observableArrayList(allUsers);
    }

    public User getUserToDelete(){
        return null;
    }


    @FXML
    public void addUser() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dialogs/addUserDialog.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add user");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(event1 -> {
                //data = FXCollections.observableArrayList(userDAO.getAll());
                tableView.setItems(data);
            });
        } catch (IOException exception) {
            Logger logger = Logger.getLogger(UsersPage.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }


}
