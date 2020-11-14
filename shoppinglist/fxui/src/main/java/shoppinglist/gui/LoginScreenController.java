package shoppinglist.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import shoppinglist.core.Client;
import shoppinglist.core.Person;
import shoppinglist.restapi.LoginResource;
import shoppinglist.storage.FileHandler;

public class LoginScreenController {

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

  private PersonDataAccess dataAccess;

  protected PersonDataAccess getDataAccess() {
    return dataAccess;
  }

  public void setDataAccess(final PersonDataAccess dataAccess) {
    this.dataAccess = dataAccess;
  }


  @FXML
  public void initialize() {
    setDataAccess(new PersonDataAccess("http://localhost:8087/index"));
  }

  /**
   * Logs in a user.
   *
   * @param e the event calling the login
   * @throws IOException if the user doesn't exist
   */

  @FXML
  void handleLogin(ActionEvent e) throws IOException {
    if (!isUserNameValid()) {
      return;
    }
    if (!checkIfFilledFields()) {
      return;
    }
    String name = usernameInputField.getText().toLowerCase();
    String password = passwordInputField.getText();


    try {
      Person p = dataAccess.putLogin(name, password);
      if (p == null) {
        errorLabel.setText("Login failed, is your password correct?");
      } else {
        Client.setCurrentPerson(p);
        mainScreen(e);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      errorLabel.setText("Login failed. Are you registred?");
    }
    System.out.println("login");
  }

  /**
   * Validates username.
   *
   * @return if username is valid
   */
  boolean isUserNameValid() {
    String name = usernameInputField.getText().toLowerCase();

    Pattern pattern = Pattern.compile("[^0-9a-zA-Z*]");
    Matcher matcher = pattern.matcher(name);
    if(matcher.find()) {
      errorLabel.setText("Illegal characters in username");
      setIllegalField(usernameInputField,true);
      return false;
    }
    setIllegalField(usernameInputField,false);
    return true;
  }

  /**
   * Sets a field to an illegal css class if illegal, to show the user that their input is wrong.
   * If it is not illegal, the method resets the field to normal.
   *
   * @param f the textbook to set
   * @param illegal if it is illegal or not
   */
  void setIllegalField(final TextField f, boolean illegal) {
    if (illegal) {
      f.getStyleClass().add("illegal");
    }
    else {
      f.getStyleClass().clear();
      f.getStyleClass().addAll("text-field", "text-input");
    }
  }

  /**
   * Checks if the name- and passwordfields are filled in.
   *
   * @return if the name- and passwordfields are filled in.
   */
  boolean checkIfFilledFields() {
    String name = usernameInputField.getText().toLowerCase();
    String password = passwordInputField.getText();
    if (name.length() == 0 && password.length() == 0) {
      errorLabel.setText("Empty username and password fields. Please fill in before continuing");
      setIllegalField(passwordInputField, true);
      setIllegalField(usernameInputField, true);
      return false;
    }
    else if (name.length() == 0) {
      errorLabel.setText("Empty username field. Please fill in before continuing");
      setIllegalField(passwordInputField, false);
      setIllegalField(usernameInputField, true);
      return false;
    }
    else if (password.length() == 0) {
      errorLabel.setText("Empty password field. Please fill in before continuing");
      setIllegalField(passwordInputField, true);
      setIllegalField(usernameInputField, false);
      return false;
    }
    return true;
  }

  /**
   * Registers a user.
   *
   * @param e the event calling the registration
   * @throws IOException if the person couldn't be written or the login throws an exception
   */
  @FXML
  void handleRegister(ActionEvent e) throws IOException {
    String name = usernameInputField.getText().toLowerCase();
    if (!isUserNameValid()) {
      return;
    }
    if (!checkIfFilledFields()) {
      return;
    }

    if (dataAccess.getPerson(name) == null) {
      String password = passwordInputField.getText();
      Person p = new Person(name);
      Client.getPasswords().setPassword(p, password);
      dataAccess.putRegister(p, password);
      FileHandler.writePasswords(Client.getPasswords());
      System.out.println("register");
      handleLogin(e);
    } else {
      errorLabel.setText("Username taken");
    }
  }

  /**
   * Loads the main screen of the gui.
   *
   * @param e the event calling the method
   */
  void mainScreen(ActionEvent e) throws IOException {
    Parent loginParent = FXMLLoader.load(getClass()
        .getResource("/resources/shoppinglist/gui/App.fxml"));
    Scene loginScene = new Scene(loginParent);
    loginScene.getStylesheets().add(getClass()
        .getResource("/resources/shoppinglist/gui/style.css").toExternalForm());
    Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    appStage.setScene(loginScene);
  }
}