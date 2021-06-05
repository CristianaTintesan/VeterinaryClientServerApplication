package Controllers;

import Client.Client;
import Controllers.Dialogs.EditAnimalDialog;
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
import model.Animal;
import model.CurrentUser;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.Request;


public class AnimalsPage implements Initializable {
    //private AnimalDAO animalDAO;
    private CurrentUser currentUser;

    private static ObservableList<Animal> data;
    @FXML
    private TableView<Animal> tableView;
    @FXML
    private TableColumn<Animal, Integer> id;
    @FXML
    private TableColumn<Animal, String> animalName;
    @FXML
    private TableColumn<Animal, Integer> animalOwnerID;

    @FXML
    private Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //animalDAO = new AnimalDAO();
        currentUser = CurrentUser.getInstance();
        if (currentUser.isAdmin())
            addButton.setVisible(true);
        //data = FXCollections.observableArrayList(animalDAO.getAll());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        animalName.setCellValueFactory(new PropertyValueFactory<>("name"));
        animalOwnerID.setCellValueFactory(new PropertyValueFactory<>("ownerID"));
        tableView.setItems(data);


        if (currentUser.isAdmin()) {
            tableView.setRowFactory(
                    tableView -> {
                        final TableRow<Animal> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem editItem = new MenuItem("Edit");
                        editItem.setOnAction(event -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass()
                                        .getResource("/Views/Dialogs/editAnimalDialog.fxml"));
                                Parent root = loader.load();
                                EditAnimalDialog editPopup = loader.getController();
                                editPopup.setData(row.getItem().getId(), row.getItem()
                                        .getOwnerID(), row.getItem()
                                        .getName());
                                Stage stage = new Stage();
                                stage.setTitle("Edit animal");
                                stage.setScene(new Scene(root));
                                stage.show();
                                stage.setOnCloseRequest(event1 -> {
                                    //data = FXCollections.observableArrayList(animalDAO.getAll());
                                    tableView.setItems(data);
                                });
                            } catch (IOException exception) {
                                Logger logger = Logger.getLogger(AnimalsPage.class.getName());
                                logger.log(Level.INFO,"exception message");
                            }
                        });
                        MenuItem removeItem = new MenuItem("Delete");
                        removeItem.setOnAction(event -> {
                            tableView.getItems().remove(row.getItem());
                            int idAnimal=row.getItem().getId();
                            try {
                                deleteAnimal(String.valueOf(idAnimal));
                            } catch (IOException e) {
                                Logger logger = Logger.getLogger(AnimalsPage.class.getName());
                                logger.log(Level.INFO,"exception message");
                            }
                            //animalDAO.delete(row.getItem().getId());
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

    public void deleteAnimal(String idAnimal) throws IOException {

        Request request = new Request("handleDeleteAnimal", idAnimal);
        request.setSenderType("user");
        Client.Connection.sendMessageToServer(request);
    }

    public static void viewAnimals(List<Animal> allAnimals) throws IOException {
        data = FXCollections.observableArrayList(allAnimals);
    }

    public Animal getAnimalToDelete(){
        return null;
    }

    @FXML
    public void addAnimal() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dialogs/addAnimalDialog.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add animal");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(event1 -> {
                //data = FXCollections.observableArrayList(animalDAO.getAll());
                tableView.setItems(data);
            });
        } catch (IOException exception) {
            Logger logger = Logger.getLogger(AnimalsPage.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }

}
