package shoppinglist;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

public class AppController {
    
    @FXML
    Button addItemButton;
    @FXML
    TextField itemInputField;
    @FXML
    TilePane shoppingList;
    @FXML 
    Label emptyListText;
    
    

    String itemToAdd = null;
    
    @FXML
    void handleAddItemButtonClicked() {
    	
    	shoppingList.getChildren().remove(emptyListText);
    	
    	itemToAdd = itemInputField.getText();
    	CheckBox shoppingListItem = new CheckBox(itemToAdd);
    	shoppingListItem.setPadding(new Insets(10, 10, 10, 10));
    	shoppingList.getChildren().add(shoppingListItem);
       
    }
}
