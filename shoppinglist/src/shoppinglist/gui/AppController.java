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
import javafx.scene.layout.TilePane;
import shoppinglist.core.*;
import shoppinglist.storage.FileHandler;

public class AppController {

    @FXML
    Button addItemButton;
    @FXML
    TextField itemInputField;
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

    public ShoppingList currentShoppingList = new ShoppingList("test"); //vil egt. ikke ha tittel her, men tror endringer må gjøres i ShoppingList.java
    
    String itemToAdd = null;

    /**
     * Add element to shoppinglist when button is clicked 
     */
    
    @FXML
    void handleAddItemButtonClicked() {
        
        
        shoppingList.getChildren().remove(emptyListText);
    	
    	itemToAdd = amountInputField.getText() + " " + measurementInputField.getText() + " " + itemInputField.getText();
    	CheckBox shoppingListItem = new CheckBox(itemToAdd);
    	shoppingListItem.setPadding(new Insets(10, 10, 10, 10));
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
        FileHandler.writeFile(currentShoppingList);
    }

    /**
     * Loads existing shoppinglist from file
     */
    @FXML
    void loadShoppingList(){
        currentShoppingList = FileHandler.readFile(Integer.parseInt(loadId.getText()));
        shoppingList.getChildren().clear();
        for(ShoppingElement x:currentShoppingList.getElementList()){
            itemToAdd = x.getValue()+ " " + x.getMeasurementName()+ " " + x.getName();
            CheckBox shoppingListItem = new CheckBox(itemToAdd);
            shoppingListItem.setPadding(new Insets(10, 10, 10, 10));
            shoppingList.getChildren().add(shoppingListItem);
            shoppingListItem.setOnAction(event -> handleItemShopped(x));
        }
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

    
  
}
