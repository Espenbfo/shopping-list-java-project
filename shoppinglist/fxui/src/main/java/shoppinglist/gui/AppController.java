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

public class AppController {
    private static Person currentPerson;


    @FXML
    Button addItemButton;
    @FXML
    TextField itemInputField;
    @FXML
    TextField shoppingTitleTextField;
    @FXML
    TextField amountInputField;
    @FXML
    TextField measurementInputField;
    @FXML
    TilePane shoppingList;
    @FXML 
    Label emptyListText;
    @FXML
    TextField loadId;
    @FXML 
    TextField personInputField;
    @FXML
    TilePane listsOverview;
    @FXML
    HBox hbox;
    @FXML
    TextField peopleInputField;
    @FXML
    Label loginNameLabel;

    public ShoppingList currentShoppingList; 
    
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

        ShoppingList.setCurrentMaxID(FileHandler.readMaxID());
        currentShoppingList = new ShoppingList("test");

        if (Client.getCurrentPerson() != null) {
            personInputField.setText(Client.getCurrentPerson().getUserName());
            fillTitleList();
            loginNameLabel.setText(Client.getCurrentPerson().getUserName());
        }
    }

    /**
     * Add element to shoppinglist when button is clicked 
     */
    @FXML
    void handleAddItemButtonClicked() {
        
        
        shoppingList.getChildren().remove(emptyListText);
    	
    	itemToAdd = amountInputField.getText() + " " + measurementInputField.getText() + " " + itemInputField.getText();
    	CheckBox shoppingListItem = new CheckBox(itemToAdd);
    	//shoppingListItem.setPadding(new Insets(10, 10, 10, 10));
        shoppingList.getChildren().add(shoppingListItem);

        ShoppingElement currentElement = new ShoppingElement(itemInputField.getText(), Double.parseDouble(amountInputField.getText()), measurementInputField.getText());
        currentShoppingList.addElement(currentElement);

        shoppingListItem.setOnAction(event -> handleItemShopped(currentElement));

        
    }
    /**
     * Saves shoppinglist to file 
     */
    @FXML
    void saveShoppingList(){
        String peopleText = loginNameLabel.getText() + "," + peopleInputField.getText();
        peopleText = peopleText.replaceAll("\\s","");
        String[] peopleNames = peopleText.split(",");
        for (String name : peopleNames) {
            try {
                Person p = FileHandler.readPerson(name);
                System.out.println(p);
                Integer prevList = currentShoppingList.getId();
                if (!p.getShoppingLists().contains(prevList)) {
                    p.addShoppingList(prevList);
                    FileHandler.writePerson(p);
                }
            }
            catch (Exception ex) {
                System.out.println(ex);
            }
        }
        if (currentShoppingList.getId() > FileHandler.readMaxID()) {
            FileHandler.writeMaxID(currentShoppingList.getId());
        }
        currentShoppingList.setTitle(shoppingTitleTextField.getText());
        FileHandler.writeFile(currentShoppingList);
        fillTitleList();
    }

    /**
     * Loads existing shoppinglist from file
     */
    @FXML
    void loadShoppingList(){
        loadShoppingListWithList(FileHandler.readFile(Integer.parseInt(loadId.getText())));
    }

    /**
     * Loads existing shoppinglist from file
     */
    @FXML
    void loadShoppingListWithList(ShoppingList l){
        currentShoppingList = l;
        shoppingList.getChildren().clear();
        for(ShoppingElement x:currentShoppingList.getElementList()){
            itemToAdd = x.getValue()+ " " + x.getMeasurementName()+ " " + x.getName();
            CheckBox shoppingListItem = new CheckBox(itemToAdd);
            shoppingListItem.setPadding(new Insets(10, 10, 10, 10));
            shoppingList.getChildren().add(shoppingListItem);
            shoppingListItem.setOnAction(event -> handleItemShopped(x));
        }
        shoppingTitleTextField.setText(currentShoppingList.getTitle());
    }
    /**
     * Changes status of shoppingitem from not shopped to shopped 
     * 
     * @param shoppingElement
     */
    @FXML
    void handleItemShopped(ShoppingElement shoppingElement) {
        shoppingElement.toggleShopped();
 
    }

    /**
     * Updates the list of shoppinglists, filtered by person, on the right side of the gui
     */
    void fillTitleList() {
        String personString = personInputField.getText();
        if (personString.equals("")) return;
        Person currenttPerson = dataAccess.getPerson(personString);
        listsOverview.getChildren().clear();
        for (Integer id : currenttPerson.getShoppingLists()) {
            ShoppingList l = FileHandler.readFile(id);
            System.out.println(l.getTitle());
            Pane list = new Pane();
            Label listName = new Label(l.getTitle());
            listName.setPrefWidth(1000.);
            listName.getStyleClass().add("listTitleListElement");
            listsOverview.getChildren().add(listName);


            listName.setOnMouseClicked(event -> handleListButtonClicked(l));
        }
    }
    /**
     * Finds and displays the lists of a given person
     * 
     * @param enter key to press to evoke method
     */
    @FXML
    void handlePersonInput(KeyEvent enter){

        if(enter.getCode() == KeyCode.ENTER) {
            fillTitleList();

        }
    	
    }

    /**
     * Loads the ShoppingList clicked
     * @param shoppingList The ShoppingList to load
     */
    @FXML 
    void handleListButtonClicked(ShoppingList shoppingList){
        System.out.println("clicked");
        currentShoppingList = shoppingList;
        loginNameLabel.setText(personInputField.getText());
        loadShoppingListWithList(FileHandler.readFile(currentShoppingList.getId()));

        //display clicked list
    }

    /**
     * Creates a new empty shoppinglist
     */
    @FXML
    void newList() {
        currentShoppingList = new ShoppingList("New List");
        loadShoppingListWithList(currentShoppingList);
        saveShoppingList();
    }

    /**
     * filters the list of shoppinglists
     */
    @FXML
    void sokList() {
        fillTitleList();
    }

    /**
     * loads the loginscreen
     * @param e the event that calls the scenechange
     * @throws IOException
     */
    @FXML
    void loginScreen(ActionEvent e) throws IOException {
        Parent loginParent =   FXMLLoader.load(getClass().getResource("/resources/shoppinglist/gui/LoginScreen.fxml"));
        Scene loginScene = new Scene(loginParent);
        loginScene.getStylesheets().add(getClass().getResource("/resources/shoppinglist/gui/style.css").toExternalForm());
        Stage appStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
    }



    
  
}
