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
import org.junit.jupiter.api.BeforeAll;
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


    Button addItemButton;
    TextField itemInputField;
    TextField amountInputField;
    TextField measurementInputField;
    TextField peopleInputField;
    Button saveButton;
    TextField shoppingTitleTextField;
    TextField personInputField;
    Label listLabel;
    TilePane listsOverview;
    Button backToLoginButton;
    Button newListButton;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml"));
        fxmlLoader.setController(new AppController());
        parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
        addItemButton = controller.addItemButton;
        itemInputField = controller.itemInputField;
        amountInputField = controller.amountInputField;
        measurementInputField = controller.measurementInputField;;
        peopleInputField = controller.peopleInputField;
        saveButton = controller.saveButton;
        shoppingTitleTextField = controller.shoppingTitleTextField;
        personInputField = controller.personInputField;
        listsOverview = controller.listsOverview;
        backToLoginButton = controller.backToLoginButton;
        newListButton = controller.newListButton;
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
        final Label listLabel = (Label) listsOverview.getChildren().get(0);
        clickOn(listLabel);
        Assertions.assertTrue(oldText.equals(controller.currentShoppingList.getElement(0).getName()));
        Assertions.assertTrue(controller.currentShoppingList.equals(controller.getShoppingAccess().getShoppingList(controller.currentShoppingList.getId())));
        clickOn(backToLoginButton);
    }
}
