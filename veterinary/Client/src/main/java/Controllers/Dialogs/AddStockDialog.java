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

public class AddStockDialog {

    @FXML
    private TextField type;

    @FXML
    private TextField quantity;

    @FXML
    private void saveButtonClicked(ActionEvent actionEvent) throws IOException {
        String productType = type.getText();
        String productQuantity = quantity.getText();

        Request request = new Request("handleAddProduct", productType + " " + productQuantity);
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
