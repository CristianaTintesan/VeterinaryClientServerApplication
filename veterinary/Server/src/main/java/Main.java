import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dao.DBConnection;

public class Main extends Application {
    DBConnection dbConnection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/app.fxml"));
        primaryStage.setTitle("Veterinary");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        dbConnection = DBConnection.getInstance();
        dbConnection.connectToDb();

    }

}
