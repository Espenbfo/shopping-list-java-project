package shoppinglist.it;

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
import shoppinglist.gui.PersonDataAccess;
import java.awt.*;

public class AppIT extends ApplicationTest {

    private Parent parent;
    private AppController controller;
    private Stage stage;
    private Scene scene;

    public void startApp(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/shoppinglist/gui/App.fxml"));
        parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/resources/shoppinglist/gui/style.css").toExternalForm());
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/shoppinglist/gui/LoginScreen.fxml"));
        parent = fxmlLoader.load();
        //controller = fxmlLoader.getController();
        scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/resources/shoppinglist/gui/style.css").toExternalForm());
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testController() {
        PersonDataAccess dataAccess = new PersonDataAccess("http://localhost:8087");
        Person p = new Person("per");
        System.out.println(p);
        dataAccess.putPerson(p);
        Person p2 = dataAccess.getPerson("per");
        System.out.println(p2);

        TextField usernameInputField = (TextField) parent.lookup("#usernameInputField");
        TextField passwordInputField = (TextField) parent.lookup("#passwordInputField");
        Button loginButton = (Button)parent.lookup("#loginButton");
        final Button registerButton = (Button)parent.lookup("#registerButton");

        clickOn(usernameInputField);
        write("testindivid");
        clickOn(passwordInputField);
        write("t");
        if(dataAccess.getPerson("testindivid") == null) {
            clickOn(registerButton);
        }
        else {
            clickOn(loginButton);
        }

        try {
            Thread.sleep(500);
        } catch (Exception e) {

        }

        scene = stage.getScene();
        parent = scene.getRoot();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }
        final Button clickMeButton = (Button) parent.lookup("#addItemButton");
        final TextField itemInputField = (TextField) parent.lookup("#itemInputField");
        final TextField amountInputField = (TextField) parent.lookup("#amountInputField");
        final TextField measurementInputField = (TextField) parent.lookup("#measurementInputField");
        final TextField peopleInputField = (TextField) parent.lookup("#peopleInputField");
        final Button saveButton = (Button)parent.lookup("#saveButton");
        final Button backToLoginButton = (Button)parent.lookup("#backToLoginButton");
        final TextField personInputField = (TextField)parent.lookup("#personInputField");
        final TextField shoppingTitleTextField = (TextField)parent.lookup("#shoppingTitle");

        clickOn(shoppingTitleTextField);
        write("testTitle");
        clickOn(amountInputField);
        write("1");
        clickOn(measurementInputField);
        write("testMeasurement");
        clickOn(itemInputField);
        write("testName");
        String oldText = itemInputField.getText();
        clickOn(clickMeButton);
        personInputField.setText("");

        clickOn(personInputField);
        write("testindivid");
        clickOn(peopleInputField);
        write("testindivid");
        clickOn(saveButton);
        clickOn(backToLoginButton);

        scene = stage.getScene();
        parent = scene.getRoot();

         usernameInputField = (TextField) parent.lookup("#usernameInputField");
         passwordInputField = (TextField) parent.lookup("#passwordInputField");
         loginButton = (Button)parent.lookup("#loginButton");

        clickOn(usernameInputField);
        write("testindivid");
        clickOn(passwordInputField);
        write("t");

        clickOn(loginButton);
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }

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
