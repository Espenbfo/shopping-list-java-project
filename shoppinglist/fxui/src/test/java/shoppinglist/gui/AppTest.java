package shoppinglist.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import shoppinglist.core.ShoppingElement;
import shoppinglist.core.ShoppingList;
import shoppinglist.core.Person;
import shoppinglist.core.Client;
import shoppinglist.gui.AppController;
import shoppinglist.restapi.PersonService;
import shoppinglist.storage.FileHandler;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;




public class AppTest extends ApplicationTest {

    private Parent parent;
    private Person testindivid;
    private PersonDataAccess personDataAccess;
    private ShoppingListDataAccess shoppingDataAccess;
    private AppController controller;



/*
    private ShoppingListDataAccess shoppingDataAccess;

    protected ShoppingListDataAccess getShoppingDataAccess() {
      return shoppingDataAccess;
    }

   protected void setUpShoppingListDataAccess() {
     final String serverUrlString = "http://localhost:8087/index/";
     final String clientUrlString = serverUrlString + PersonService.PERSON_SERVICE_PATH;
     shoppingDataAccess = new ShoppingListDataAccess(clientUrlString);
   }

   private PersonDataAccess personDataAccess;

    protected PersonDataAccess getPersonDataAccess() {
      return personDataAccess;
    }

   protected void setUpPersonDataAccess() {
     final String serverUrlString = "http://localhost:8087/index/";
     final String clientUrlString = serverUrlString + PersonService.PERSON_SERVICE_PATH;
     personDataAccess = new PersonDataAccess(clientUrlString);
   }

   */
    

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

    @BeforeEach
    public void setUp() {
      personDataAccess = mock(PersonDataAccess.class);
      shoppingDataAccess = mock(ShoppingListDataAccess.class);
      controller.setDataAccess(personDataAccess);
      controller.setShoppingDataAccess(shoppingDataAccess);
      testindivid = new Person("testindivid");
      personDataAccess.putRegister(testindivid, "password");
      Client.setCurrentPerson(testindivid);
      ShoppingList testlist =  new ShoppingList("testName",0,new ArrayList<>(Arrays.asList(new ShoppingElement("testName",1, "testMeasurement"))),new ArrayList(Arrays.asList("testindivid")));
      testlist.setOwner(testindivid);
      testindivid.addShoppingList(0);
      when(shoppingDataAccess.getShoppingList(0)).thenReturn(testlist);
      when(personDataAccess.getPerson(anyString())).thenReturn(testindivid);
        //FileHandler.writePasswords(Client.getPasswords());
    }

    @Test
    public void testController() {
        final Button addItemButton = (Button) parent.lookup("#addItemButton");
        final TextField itemInputField = (TextField) parent.lookup("#itemInputField");
        final TextField amountInputField = (TextField) parent.lookup("#amountInputField");
        final TextField measurementInputField = (TextField) parent.lookup("#measurementInputField");
        final TextField peopleInputField = (TextField) parent.lookup("#peopleInputField");
        final Button saveButton = (Button)parent.lookup("#saveButton");
        final TextField shoppingTitleTextField = (TextField) parent.lookup("#shoppingTitle");
        final TextField personInputField = (TextField)parent.lookup("#personInputField");
        clickOn(amountInputField);
        write("1");
        clickOn(measurementInputField);
        write("testMeasurement");
        clickOn(itemInputField);
        write("testName");
        String oldText = itemInputField.getText();
        clickOn(addItemButton);
        personInputField.setText("");
        clickOn(personInputField);
        write("testindivid");
        clickOn(peopleInputField);
        write("testindivid");
        clickOn(shoppingTitleTextField);
        write("testName");
        clickOn(saveButton);
        final TilePane listsOverview = (TilePane) parent.lookup("#listsOverview");
        final Label listLabel = (Label)listsOverview.getChildren().get(0);
        clickOn(listLabel);
        Assertions.assertTrue(oldText.equals(controller.currentShoppingList.getElement(0).getName()));
        Assertions.assertTrue(controller.currentShoppingList.equals(controller.getShoppingAccess().getShoppingList(controller.currentShoppingList.getId())));
        final Button backToLoginButton = (Button) parent.lookup("#backToLoginButton");
        clickOn(backToLoginButton);

    }

    
}
