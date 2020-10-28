package shoppinglist.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import shoppinglist.core.*;
import shoppinglist.storage.FileHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class LoginScreenController {
    private static Person currentPerson;


    @FXML
    TextField usernameInputField;
    @FXML
    PasswordField passwordInputField;
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
    @FXML
    Label errorLabel;

    String itemToAdd = null;

    /**
     * Add element to shoppinglist when button is clicked 
     */

    /**
     * Logs in a user
     * @param e the event calling the login
     * @throws IOException if the user doesn't exist
     */
    @FXML
    void handleLogin(ActionEvent e) throws IOException {

        String name = usernameInputField.getText();
        String password = passwordInputField.getText();

        try {
            Person p = FileHandler.readPerson(name);
            System.out.println(p.getUserName());
            Client.setCurrentPerson(p);
            if (Client.getPasswords().checkPassword(Client.getCurrentPerson(), password)) {
                mainScreen(e);
            }
            else {
                errorLabel.setText("Login mislykket, er passordet ditt riktig?");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            errorLabel.setText("Login mislykket, har du husket å registrere profilen din?");
        }
        System.out.println("login");

    }

    /**
     * Registers a user
     * @param e the event calling the registration
     * @throws IOException if the person couldn't be written or the login throws an exception
     */
    @FXML
    void handleRegister(ActionEvent e) throws IOException {
        String name = usernameInputField.getText();
        String password = passwordInputField.getText();
        Person p = new Person(name);
        byte[] salt = p.getSalt();
        Client.getPasswords().setPassword(p,password);
        FileHandler.writePerson(p);
        FileHandler.writePasswords(Client.getPasswords());
        System.out.println("register");
        handleLogin(e);
    }

    /**
     * Loads the main screen of the gui
     * @param e the event calling the method
     * @throws IOException
     */
    void mainScreen(ActionEvent e) throws IOException {
        Parent loginParent =   FXMLLoader.load(getClass().getResource("/resources/shoppinglist/gui/App.fxml"));
        Scene loginScene = new Scene(loginParent);
        loginScene.getStylesheets().add(getClass().getResource("/resources/shoppinglist/gui/style.css").toExternalForm());
        Stage appStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
    }

    
  
}
