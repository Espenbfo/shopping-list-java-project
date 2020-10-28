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
import shoppinglist.core.Passwords;
import shoppinglist.storage.FileHandler;

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

        Client.setPasswords(FileHandler.readPasswords());
        if (Client.getPasswords() == null) {
            Client.setPasswords(new Passwords());
        }
    }


    
    @Test
    public void testRegister() {
        final Button registerButton = (Button) parent.lookup("#registerButton");
        final TextField usernameInputField = (TextField) parent.lookup("#usernameInputField");
        final TextField passwordInputField = (TextField) parent.lookup("#passwordInputField");

        clickOn(usernameInputField);
        write("Gud");
        clickOn(passwordInputField);
        write("duG");
        clickOn(registerButton);
        Assertions.assertTrue(Client.getCurrentPerson().getUserName().equals("Gud"));
    }
    
}
