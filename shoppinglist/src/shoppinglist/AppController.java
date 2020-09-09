package shoppinglist;

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
    

    ShoppingList currentShoppingList = new ShoppingList("test"); //vil egt. ikke ha tittel her, men tror endringer må gjøres i ShopppingList.java
    
    String itemToAdd = null;
    
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

    @FXML
    void handleItemShopped(ShoppingElement shoppingElement) {
        shoppingElement.toggleShopped();
 
    }

    
  
}
