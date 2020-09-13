package shoppinglist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class AppTest extends ApplicationTest {

    private Parent parent;
    private AppController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml"));
        parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void testController() {
        final Button clickMeButton = (Button) parent.lookup("#addItemButton");
        String oldText = clickMeButton.getText();
        clickOn(clickMeButton);
        Assertions.assertFalse(clickMeButton.getText().equals(oldText));
    }
}
