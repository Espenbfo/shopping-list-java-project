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
import shoppinglist.gui.LoginScreenController;
import shoppinglist.core.ShoppingList;
import shoppinglist.core.Person;
import shoppinglist.core.Client;

import java.awt.*;

public class LoginScreenControllerTest extends ApplicationTest {

    private Parent parent;
    private LoginScreenController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/shoppinglist/gui/LoginScreen.fxml"));
        parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/resources/shoppinglist/gui/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }


    
    @Test
    public void testLogIn() {
        final Button loginButton = (Button) parent.lookup("#loginButton");
        final TextField usernameInputField = (TextField) parent.lookup("#usernameInputField");
        clickOn(usernameInputField);
        write("Gud");
        clickOn(loginButton);

        Assertions.assertTrue(Client.getCurrentPerson().getUserName().equals("Gud"));
    }
    
}
