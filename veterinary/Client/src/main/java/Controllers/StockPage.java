package Controllers;

import Client.Client;
import Controllers.Dialogs.EditStockDialog;
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
import model.Stock;
import utils.Request;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockPage implements Initializable {

    private final CurrentUser currentUser = CurrentUser.getInstance();

    private static ObservableList<Stock> data;
    @FXML
    private TableView<Stock> tableView;
    @FXML
    private TableColumn<Stock, Integer> id;
    @FXML
    private TableColumn<Stock, String> productType;
    @FXML
    private TableColumn<Stock, Integer> productQuantity;
    @FXML
    private Button addButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (currentUser.isAdmin())
            addButton.setVisible(true);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        productType.setCellValueFactory(new PropertyValueFactory<>("type"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableView.setItems(data);


        if (currentUser.isAdmin()) {
            tableView.setRowFactory(
                    tableView -> {
                        final TableRow<Stock> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem editItem = new MenuItem("Edit");
                        editItem.setOnAction(event -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass()
                                        .getResource("/Views/Dialogs/editStockDialog.fxml"));
                                Parent root = loader.load();
                                EditStockDialog editPopup = loader.getController();
                                editPopup.setData(row.getItem().getId(), row.getItem()
                                        .getType(), row.getItem()
                                        .getQuantity());
                                Stage stage = new Stage();
                                stage.setTitle("Edit stock");
                                stage.setScene(new Scene(root));
                                stage.show();
                                stage.setOnCloseRequest(event1 -> {
                                    tableView.setItems(data);
                                });
                            } catch (IOException exception) {
                                Logger logger = Logger.getLogger(StockPage.class.getName());
                                logger.log(Level.INFO,"exception message");
                            }
                        });
                        MenuItem removeItem = new MenuItem("Delete");
                        removeItem.setOnAction(event -> {
                            tableView.getItems().remove(row.getItem());
                            int idStock=row.getItem().getId();
                            try {
                                deleteProduct(String.valueOf(idStock));
                            } catch (IOException e) {
                                Logger logger = Logger.getLogger(StockPage.class.getName());
                                logger.log(Level.INFO,"exception message");
                            }
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

    public void deleteProduct(String idProduct) throws IOException {

        Request request = new Request("handleDeleteProduct", idProduct);
        request.setSenderType("user");
        Client.Connection.sendMessageToServer(request);
    }

    public static void viewProducts(List<Stock> allProducts) throws IOException {
        data = FXCollections.observableArrayList(allProducts);
    }

    @FXML
    public void addProduct() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dialogs/addStockDialog.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add product");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(event1 -> {
                tableView.setItems(data);
            });
        } catch (IOException exception) {
            Logger logger = Logger.getLogger(StockPage.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }



}
