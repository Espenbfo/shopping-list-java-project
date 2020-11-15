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
import javafx.scene.control.Label;
import shoppinglist.gui.LoginScreenController;
import shoppinglist.gui.PersonDataAccess;
import shoppinglist.core.ShoppingList;
import shoppinglist.core.Person;
import shoppinglist.core.Client;
import shoppinglist.core.Passwords;
import shoppinglist.storage.FileHandler;
import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;


public class LoginScreenControllerTest extends ApplicationTest {

    private Parent parent;
    private LoginScreenController controller;
    private AppController appController;
    private PersonDataAccess dataAccess;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/shoppinglist/gui/LoginScreen.fxml"));
        parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        dataAccess = mock(PersonDataAccess.class);
        controller.setDataAccess(dataAccess);
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/resources/shoppinglist/gui/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }


    
    @Test
    public void testRegister() {
        when(dataAccess.getPerson(any())).thenReturn(null);
        when(dataAccess.putLogin("testindivid","duG")).thenReturn(new Person("testindivid"));
        final Button registerButton = (Button) parent.lookup("#registerButton");
        final TextField usernameInputField = (TextField) parent.lookup("#usernameInputField");
        final TextField passwordInputField = (TextField) parent.lookup("#passwordInputField");
        clickOn(usernameInputField);
        write("testindivid");
        clickOn(passwordInputField);
        write("duG");
        clickOn(registerButton);
        System.out.println(Client.getCurrentPerson().getUserName());
        Assertions.assertTrue(Client.getCurrentPerson().getUserName().equals("testindivid"));
    }

    @Test
    public void testLogin(){
        when(dataAccess.putLogin("testindivid","duG")).thenReturn(new Person("testindivid"));
        final Button loginButton = (Button) parent.lookup("#loginButton");
        final TextField usernameInputField = (TextField) parent.lookup("#usernameInputField");
        final TextField passwordInputField = (TextField) parent.lookup("#passwordInputField");
        clickOn(usernameInputField);
        write("testindivid");
        clickOn(passwordInputField);
        write("duG");
        clickOn(loginButton);
        System.out.println(Client.getCurrentPerson().getUserName());
        Assertions.assertTrue(Client.getCurrentPerson().getUserName().equals("testindivid"));
    }

    @Test
    public void testLoginNoName(){
        when(dataAccess.putLogin(anyString(),anyString())).thenReturn(new Person("failed"));
        final Button loginButton = (Button) parent.lookup("#loginButton");
        final Label errorLabel = (Label) parent.lookup("#error");
        clickOn(loginButton);
        System.out.println(Client.getCurrentPerson().getUserName());
        Assertions.assertTrue(errorLabel.getText().equals("Empty username and password fields. Please fill in before continuing"));
    }

    @Test
    public void testRegisterNoName(){
        when(dataAccess.putLogin(anyString(),anyString())).thenReturn(new Person("failed"));
        final Button registerButton = (Button) parent.lookup("#registerButton");
        final Label errorLabel = (Label) parent.lookup("#error");
        clickOn(registerButton);
        System.out.println(Client.getCurrentPerson().getUserName());
        Assertions.assertTrue(errorLabel.getText().equals("Empty username and password fields. Please fill in before continuing"));
    }

    @Test
    public void testLoginWrongName(){
        when(dataAccess.putLogin(anyString(),anyString())).thenReturn(null);
        final Label errorLabel = (Label) parent.lookup("#error");
        final Button loginButton = (Button) parent.lookup("#loginButton");
        final TextField usernameInputField = (TextField) parent.lookup("#usernameInputField");
        final TextField passwordInputField = (TextField) parent.lookup("#passwordInputField");
        clickOn(usernameInputField);
        write("testindivid");
        clickOn(passwordInputField);
        write("duG");
        clickOn(loginButton);
        System.out.println(Client.getCurrentPerson().getUserName());
        Assertions.assertTrue(errorLabel.getText().equals("Login failed, is your password correct?"));
    }

    @Test
    public void testLoginInvalidName(){
        when(dataAccess.putLogin(anyString(),anyString())).thenReturn(null);
        final Label errorLabel = (Label) parent.lookup("#error");
        final Button loginButton = (Button) parent.lookup("#loginButton");
        final TextField usernameInputField = (TextField) parent.lookup("#usernameInputField");
        final TextField passwordInputField = (TextField) parent.lookup("#passwordInputField");
        clickOn(usernameInputField);
        write("@%32");
        clickOn(passwordInputField);
        write("duG");
        clickOn(loginButton);
        System.out.println(Client.getCurrentPerson().getUserName());
        Assertions.assertTrue(errorLabel.getText().equals("Illegal characters in username"));
    }
    
}
