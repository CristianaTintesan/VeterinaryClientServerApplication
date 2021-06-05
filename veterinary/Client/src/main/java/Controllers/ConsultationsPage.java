package Controllers;

import Client.Client;
import Controllers.Dialogs.EditAnimalDialog;
import Controllers.Dialogs.EditConsultationDialog;
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
import model.Consulation;
import utils.Request;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultationsPage implements Initializable {
    //private final CurrentUser currentUser = CurrentUser.getInstance();
    //private final ConsultationDAO consultationDAO = new ConsultationDAO();
    private static ObservableList<Consulation> data;

    @FXML
    private TableView<Consulation> tableView;
    @FXML
    private TableColumn<Consulation, Integer> id;
    @FXML
    private TableColumn<Consulation, Integer> animalid;
    @FXML
    private TableColumn<Consulation, Integer> animalname;

    @FXML
    private TableColumn<Consulation, Integer> ownerid;
    @FXML
    private TableColumn<Consulation, Integer> ownername;
    @FXML
    private TableColumn<Consulation, Date> date;

    @FXML
    private TableColumn<Consulation, Integer> status;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //data = FXCollections.observableArrayList(consultationDAO.getAll());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        animalid.setCellValueFactory(new PropertyValueFactory<>("animalID"));
        animalname.setCellValueFactory(new PropertyValueFactory<>("animalName"));
        ownerid.setCellValueFactory(new PropertyValueFactory<>("ownerID"));
        ownername.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableView.setItems(data);


        tableView.setRowFactory(
                tableView -> {
                    final TableRow<Consulation> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem editConsultationItem = new MenuItem("Edit consultation");
                    editConsultationItem.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass()
                                    .getResource("/Views/Dialogs/editConsultationDialog.fxml"));
                            Parent root = loader.load();
                            EditConsultationDialog editPopup = loader.getController();
                            editPopup.setData(row.getItem().getId(), row.getItem().getAnimalID(), row.getItem()
                                    .getDate(), row.getItem().getStatus());
                            Stage stage = new Stage();
                            stage.setTitle("Edit consultation");
                            stage.setScene(new Scene(root));
                            stage.show();
                            stage.setOnCloseRequest(event1 -> {
                                //data = FXCollections.observableArrayList(consultationDAO.getAll());
                                tableView.setItems(data);
                            });
                        } catch (IOException exception) {
                            Logger logger = Logger.getLogger(ConsultationsPage.class.getName());
                            logger.log(Level.INFO,"exception message");
                        }
                    });
                    MenuItem editAnimalItem = new MenuItem("Edit animal");
                    editAnimalItem.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass()
                                    .getResource("/Views/Dialogs/editAnimalDialog.fxml"));
                            Parent root = loader.load();
                            EditAnimalDialog editPopup = loader.getController();
                            editPopup.setData(row.getItem().getAnimalID(), row.getItem()
                                    .getOwnerID(), row.getItem()
                                    .getAnimal().getName());
                            Stage stage = new Stage();
                            stage.setTitle("Edit animal");
                            stage.setScene(new Scene(root));
                            stage.show();
                            stage.setOnCloseRequest(event1 -> {
                                //data = FXCollections.observableArrayList(consultationDAO.getAll());
                                tableView.setItems(data);
                            });
                        } catch (IOException exception) {
                            Logger logger = Logger.getLogger(ConsultationsPage.class.getName());
                            logger.log(Level.INFO,"exception message");
                        }
                    });
                    MenuItem removeItem = new MenuItem("Delete");
                    removeItem.setOnAction(event -> {
                        tableView.getItems().remove(row.getItem());

                        int idConsultation=row.getItem().getId();
                        try {
                            deleteConsultation(String.valueOf(idConsultation));
                        } catch (IOException e) {
                            Logger logger = Logger.getLogger(ConsultationsPage.class.getName());
                            logger.log(Level.INFO,"exception message");
                        }

                        //consultationDAO.delete(row.getItem().getId());
                    });
                    rowMenu.getItems().addAll(editConsultationItem, editAnimalItem, removeItem);
                    row.contextMenuProperty().bind(
                            Bindings.when(row.emptyProperty())
                                    .then((ContextMenu) null)
                                    .otherwise(rowMenu));
                    return row;
                });
    }

    public void deleteConsultation(String idConsultation) throws IOException {

        Request request = new Request("handleDeleteConsultation", idConsultation);
        request.setSenderType("user");
        Client.Connection.sendMessageToServer(request);
    }

    public static void viewConsultation(List<Consulation> allConsultations) throws IOException {
        data = FXCollections.observableArrayList(allConsultations);
    }


    @FXML
    private void addConsultation() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dialogs/addConsultationDialog.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add consultation");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(event1 -> {
                //data = FXCollections.observableArrayList(consultationDAO.getAll());
                tableView.setItems(data);
            });
        } catch (IOException exception) {
            Logger logger = Logger.getLogger(ConsultationsPage.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }


    public Consulation getConsultationToDelete() {
        Consulation cons = new Consulation();
        cons.setId(1);

        return cons;
    }
}
