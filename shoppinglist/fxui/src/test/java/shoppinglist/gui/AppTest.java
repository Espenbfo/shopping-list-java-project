package shoppinglist.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import java.net.URL;
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
import shoppinglist.restapi.PersonService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;




import java.awt.*;


@ExtendWith(MockitoExtension.class)
public class AppTest extends ApplicationTest {

    private Parent parent;
    private Person testindivid;
    @Mock PersonDataAccess personDataAccess;
    @Mock ShoppingListDataAccess shoppingDataAccess;
    @InjectMocks private AppController controller;



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
        this.controller = fxmlLoader.getController();
        //setUpShoppingListDataAccess();
        //setUpPersonDataAccess();
        //this.controller.setShoppingDataAccess(getShoppingDataAccess());
        //this.controller.setDataAccess(getPersonDataAccess());
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/resources/shoppinglist/gui/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
      personDataAccess = mock(PersonDataAccess.class);
      shoppingDataAccess = mock(ShoppingListDataAccess.class);
      controller = mock(AppController.class);
      testindivid = new Person("testindivid");
      personDataAccess.putRegister(testindivid, "password");
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
        final TextField personInputField = (TextField)parent.lookup("#personInputField");
        clickOn(amountInputField);
        amountInputField.setText("");
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
        //clickOn(saveButton);
        System.out.println(controller.currentShoppingList);

        //Assertions.assertTrue(oldText.equals(controller.currentShoppingList.getElement(0).getName()));
        //Assertions.assertTrue(controller.currentShoppingList.equals(dataAccess.(controller.currentShoppingList.getId())));
    }

    
}
