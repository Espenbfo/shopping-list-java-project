package shoppinglist.gui;

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
import shoppinglist.core.Person;
import shoppinglist.core.Client;
import shoppinglist.storage.FileHandler;

import java.awt.*;

public class AppTest extends ApplicationTest {

    private Parent parent;
    private AppController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/shoppinglist/gui/App.fxml"));
        parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/resources/shoppinglist/gui/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testController() {
        final Button clickMeButton = (Button) parent.lookup("#addItemButton");
        final TextField itemInputField = (TextField) parent.lookup("#itemInputField");
        final TextField amountInputField = (TextField) parent.lookup("#amountInputField");
        final TextField measurementInputField = (TextField) parent.lookup("#measurementInputField");
        final TextField peopleInputField = (TextField) parent.lookup("#peopleInputField");
        final Button saveButton = (Button)parent.lookup("#saveButton");
        final TextField personInputField = (TextField)parent.lookup("#personInputField");
        clickOn(amountInputField);
        amountInputField.setText("");
        write("1");
        clickOn(measurementInputField);
        write("testMeasurement");
        clickOn(itemInputField);
        write("testName");
        String oldText = itemInputField.getText();
        clickOn(clickMeButton);
        personInputField.setText("");

        Person testIndivid = new Person("testIndivid");
        FileHandler.writePerson(testIndivid);
        clickOn(personInputField);
        write("TestIndivid");
        clickOn(peopleInputField);
        write("TestIndivid");
        clickOn(saveButton);
        System.out.println(controller.currentShoppingList);
        Assertions.assertTrue(oldText.equals(controller.currentShoppingList.getElement(0).getName()));
        Assertions.assertTrue(controller.currentShoppingList.equals(FileHandler.readFile(controller.currentShoppingList.getId())));
    }

    /*
    @Test
    public void testLogIn(){
        final Button loginButton = (Button) parent.lookup("#loginButton");
        final TextField usernameField = (TextField) parent.lookup("#usernameField");
        clickOn(usernameField);
        write("Gud");
        clickOn(loginButton);
        Assertions.assertTrue(Client.getCurrentPerson().getUserName().equals("Gud"));
    }
    */
}
