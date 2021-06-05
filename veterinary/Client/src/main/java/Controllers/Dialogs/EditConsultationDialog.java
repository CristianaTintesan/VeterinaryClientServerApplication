package Controllers.Dialogs;

import Client.Client;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.Request;
import java.io.IOException;
import java.sql.Date;


public class EditConsultationDialog {
    private int consultationID;
    @FXML
    private TextField animal;
    @FXML
    private DatePicker date;
    @FXML
    public ComboBox status = new ComboBox();

    public void setData(int consultationID, int animalID, Date date, String status) {
        this.consultationID = consultationID;
        this.animal.setText(String.valueOf(animalID));
        this.date.setValue(date.toLocalDate());
        //this.status.setText(status);
    }


    @FXML
    void saveButtonClicked(ActionEvent actionEvent) throws IOException {

        String animalId = animal.getText();
        String dateConsultation = Date.valueOf(date.getValue()).toString();
        String idConsultation= String.valueOf(consultationID);

        SingleSelectionModel selectionModel = status.getSelectionModel();
        TextField selection = new TextField(String.valueOf(selectionModel.getSelectedIndex()));
        selectionModel.selectedIndexProperty().addListener((Observable o) -> {
            selection.setText(String.valueOf(selectionModel.getSelectedIndex()));
        });

        String  aux= selection.getText();
        String consultationStatus = null;

        if ("1".equals(aux)){
            consultationStatus = "Done";
        }else if ("0".equals(aux))
            consultationStatus = "Schedule";
        else if ("2".equals(aux)){
            consultationStatus ="Progress";
        }


        Request request = new Request("handleUpdateConsultation", animalId + " " + dateConsultation + " " + idConsultation + " " + consultationStatus);
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
