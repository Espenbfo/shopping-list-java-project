package shoppinglist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import shoppinglist.gui.AppController;
import shoppinglist.core.ShoppingList;

public class AppTest extends ApplicationTest {

    private Parent parent;
    private AppController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/App.fxml"));
        parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void testController() {
        final Button clickMeButton = (Button) parent.lookup("#addItemButton");
        final TextField itemInputField = (TextField) parent.lookup("#itemInputField");
        final TextField amountInputField = (TextField) parent.lookup("#amountInputField");
        final TextField measurementInputField = (TextField) parent.lookup("#measurementInputField");
        clickOn(amountInputField);
        write("1");
        clickOn(measurementInputField);
        write("testMeasurement");
        clickOn(itemInputField);
        write("testName");
        String oldText = itemInputField.getText();
        clickOn(clickMeButton);
        System.out.println(controller.currentShoppingList);
        Assertions.assertTrue(oldText.equals(controller.currentShoppingList.getElement(0).getName()));
    }
}
