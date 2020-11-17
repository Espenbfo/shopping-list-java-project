package shoppinglist.gui;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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

  private PersonDataAccess dataAccess;

  protected PersonDataAccess getDataAccess() {
    return dataAccess;
  }

  public void setDataAccess(final PersonDataAccess dataAccess) {
    this.dataAccess = dataAccess;
  }


  @FXML
  public void initialize() {
    setDataAccess(new PersonDataAccess("http://localhost:8087"));
  }

  /**
   * Logs in a user.
   *
   * @param e the event calling the login
   * @throws IOException if the user doesn't exist
   */

  @FXML
  void handleLogin(ActionEvent e) throws IOException {

    //Checks if the username has illegal characters
    if (!isUserNameValid(e)) {
      return;
    }

    //Checks if both the user- and passwordfields are filled in
    if (!checkIfFilledFields(e)) {
      return;
    }

    //Gets the valid name and password from their fields
    String name = usernameInputField.getText().toLowerCase();
    String password = passwordInputField.getText();


    try {
      //Send the login information to the server
      Person p = dataAccess.putLogin(name, password);

      //if p == null, the login has failed
      if (p == null) {
        errorLabel.setText("Login failed, is your username and password correct?");
      } else {
        Client.setCurrentPerson(p);
        mainScreen(e);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Validates username.
   *
   * @Param e necessary for the error tooltip.
   * @return if username is valid
   */
  boolean isUserNameValid(ActionEvent e) {
    //The written username
    String name = usernameInputField.getText().toLowerCase();

    //The regex pattern the username has to not follow.
    //[^0-9a-zA-Z*] means no characters in the groups 0-9, a-z and A-Z.
    Pattern pattern = Pattern.compile("[^0-9a-zA-Z*]");
    Matcher matcher = pattern.matcher(name);

    //Sees if the username follows the illegal pattern
    if (matcher.find()) {
      errorLabel.setText("Illegal characters in username");

      //Creates a tooltip above the username input field.
      showError(usernameInputField, "Invalid username", e, -20);
      setIllegalField(usernameInputField, true);
      return false;
    }

    //Validates the usernamefield
    setIllegalField(usernameInputField, false);

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
    } else {

      //Clears all css classes
      f.getStyleClass().clear();

      //Adds the default classes back
      f.getStyleClass().addAll("text-field", "text-input");

      //Removes all tooltips
      f.setTooltip(null);
    }
  }

  /**
   * Checks if the name- and passwordfields are filled in.
   *
   * @Param e necessary for the error tooltip.
   * @return if the name- and passwordfields are filled in.
   */
  boolean checkIfFilledFields(ActionEvent e) {
    //Gets the username and the password
    String name = usernameInputField.getText().toLowerCase();
    String password = passwordInputField.getText();

    if (name.length() == 0 && password.length() == 0) {
      //If they both are empty:

      showError(usernameInputField, "This field is empty", e, -20);
      showError(passwordInputField, "This field is empty", e, -10);
      errorLabel.setText("Empty username and password fields. Please fill in before continuing");
      setIllegalField(passwordInputField, true);
      setIllegalField(usernameInputField, true);
      return false;
    } else if (name.length() == 0) {
      //If only the username is empty:

      showError(usernameInputField, "This field is empty", e, -20);
      errorLabel.setText("Empty username field. Please fill in before continuing");
      setIllegalField(passwordInputField, false);
      setIllegalField(usernameInputField, true);
      return false;
    } else if (password.length() == 0) {
      //If just the password is empty:

      showError(passwordInputField, "This field is empty", e, -10);
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
    //Checks if the username has illegal characters
    if (!isUserNameValid(e)) {
      return;
    }

    //Checks if all field are filled
    if (!checkIfFilledFields(e)) {
      return;
    }

    //Gets the name
    String name = usernameInputField.getText().toLowerCase();

    //Checks if the person already exists
    if (dataAccess.getPerson(name) == null) {

      //Gets the password
      String password = passwordInputField.getText();

      //Creates a new Person
      Person p = new Person(name);

      //Registers the user-password combo to the server.
      dataAccess.putRegister(p, password);

      //Logs in automatically with the new user.
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
    //Gets the new parent.
    Parent loginParent = FXMLLoader.load(getClass()
        .getResource("/resources/shoppinglist/gui/App.fxml"));

    //Creates a new scene with the new parent.
    Scene loginScene = new Scene(loginParent);

    //Loads the stylecheet.
    loginScene.getStylesheets().add(getClass()
        .getResource("/resources/shoppinglist/gui/style.css").toExternalForm());

    //Gets the current stage.
    Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();

    //Sets the new scene
    appStage.setScene(loginScene);
  }

  /**
   * Shows an error message above a textfield.
   *
   * @param textfield The textfield
   * @param tooltipText The text in the error message
   * @param e the actionevent. This is used to find the right coordinates.
   */
  public static void showError(final TextField textfield,
                               final String tooltipText,
                               final ActionEvent e,
                               final int yshift) {

    //Creates a new tooltip
    final Tooltip customTooltip = new Tooltip();

    //Sets the text to the tooltip
    customTooltip.setText(tooltipText);

    //Gets the point2d of the textfield, used to find the coordinates later
    Point2D point2D = textfield.localToScene(0, 0);

    //Sets the tooltip to our textfield
    textfield.setTooltip(customTooltip);

    //Sets the tooltip to autohide
    customTooltip.setAutoHide(true);

    //Gets the current stage
    Stage owner = (Stage) ((Node) e.getSource()).getScene().getWindow();

    //Shows the tooltip at the textfield, shifted by yShift pixels in the y direction
    customTooltip.show(owner,
            point2D.getX() + textfield.getScene().getX() + textfield.getScene().getWindow().getX(),
            point2D.getY() + textfield.getScene().getY() + textfield.getScene().getWindow().getY()
                    + yshift);

  }
}