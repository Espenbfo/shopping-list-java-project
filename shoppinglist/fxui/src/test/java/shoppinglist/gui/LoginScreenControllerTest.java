package shoppinglist.gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import shoppinglist.core.ShoppingList;
import shoppinglist.core.Person;
import shoppinglist.core.Client;
import shoppinglist.core.Passwords;
import shoppinglist.gui.LoginScreenController;
import shoppinglist.gui.PersonDataAccess;
import shoppinglist.storage.FileHandler;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


public class LoginScreenControllerTest extends ApplicationTest {


    private Parent parent;
    private LoginScreenController controller;
    private AppController appController;
    private PersonDataAccess dataAccess;


    Label errorLabel;
    Button loginButton;
    Button registerButton;
    TextField usernameInputField;
    TextField passwordInputField;
    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        controller = spy(new LoginScreenController());
        fxmlLoader.setController(controller);
        parent = fxmlLoader.load();
        dataAccess = mock(PersonDataAccess.class);
        controller.setDataAccess(dataAccess);
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();

        errorLabel = controller.errorLabel;
        loginButton = controller.loginButton;
        registerButton = controller.registerButton;
        usernameInputField = controller.usernameInputField;
        passwordInputField = controller.passwordInputField;
    }


    
    @Test
    public void testRegister() {
        when(dataAccess.getPerson(any())).thenReturn(null);
        //preventing it from trying to change screen to App.fxml
        try {
            doNothing().when(controller).mainScreen(any());
         }catch (IOException e){
            //if this happens the test should still finish albeit with some extra exceptions
            e.printStackTrace();
        }
        when(dataAccess.postLogin("testindivid","duG")).thenReturn(new Person("testindivid"));
        clickOn(usernameInputField);
        write("testindivid");
        clickOn(passwordInputField);
        write("duG");
        clickOn(registerButton);
        Assertions.assertTrue(Client.getCurrentPerson().getUserName().equals("testindivid"));
    }

    @Test
    public void testLogin(){
        when(dataAccess.postLogin("testindivid","duG")).thenReturn(new Person("testindivid"));
        //preventing it from trying to change screen to App.fxml
        try {
            doNothing().when(controller).mainScreen(any());
        }catch (IOException e){
            //if this happens the test should still finish albeit with some extra exceptions
            e.printStackTrace();
        }
        clickOn(usernameInputField);
        write("testindivid");
        clickOn(passwordInputField);
        write("duG");
        clickOn(loginButton);
        Assertions.assertTrue(Client.getCurrentPerson().getUserName().equals("testindivid"));
    }

    @Test
    public void testLoginNoName(){
        when(dataAccess.postLogin(anyString(),anyString())).thenReturn(new Person("failed"));
        usernameInputField.setText("");
        clickOn(loginButton);
        Assertions.assertTrue(errorLabel.getText().equals("Empty username and password fields. Please fill in before continuing"));
    }

    @Test
    public void testRegisterNoName(){
        when(dataAccess.postLogin(anyString(),anyString())).thenReturn(new Person("failed"));
        usernameInputField.setText("");
        clickOn(registerButton);;
        Assertions.assertTrue(errorLabel.getText().equals("Empty username and password fields. Please fill in before continuing"));
    }

    @Test
    public void testLoginWrongName(){
        when(dataAccess.postLogin(anyString(),anyString())).thenReturn(null);

        clickOn(usernameInputField);
        write("testindivid");
        clickOn(passwordInputField);
        write("duG");
        clickOn(loginButton);
        Assertions.assertTrue(errorLabel.getText().equals("Login failed, is your username and password correct?"));
    }

    @Test
    public void testLoginInvalidName(){
        when(dataAccess.postLogin(anyString(),anyString())).thenReturn(null);

        clickOn(usernameInputField);
        write("@%32");
        clickOn(passwordInputField);
        write("duG");
        clickOn(loginButton);
        Assertions.assertTrue(errorLabel.getText().equals("Illegal characters in username"));
    }
}
