import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.Test;
import org.testfx.api.FxRobotException;
import org.testfx.api.FxToolkit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;


public class UITests extends BaseTest {

    final String username_field = "#usernameField";
    final String password_field = "#passwordField";


    @Test(expected = FxRobotException.class)
    public void testInexistentItemClick() {
        clickOn("#inexistentItemId");
    }

    @Test
    public void testLoginInputFields() {
        clickOn(username_field).write("admin");
        clickOn(password_field).write("admin");

        verifyThat(username_field, (TextField textfield) -> "admin".equals(textfield.getText()));
        verifyThat(password_field, (PasswordField textfield) -> "admin".equals(textfield.getText()));

    }

    @Test
    public void changeScene() throws IOException, TimeoutException {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/Views/animals.fxml"));
        Parent root = loader.load();
        FxToolkit.setupStage(stage -> stage.setScene(new Scene(root, 600, 400)));

    }
}
