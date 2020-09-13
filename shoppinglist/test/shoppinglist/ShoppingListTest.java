package shoppinglist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class ShoppingListTest extends ApplicationTest {

    private Parent parent;
    private AppController controller;


    @Test
    public void testShoppingList() {
        ShoppingList l2 = new ShoppingList("Liste 2");
        l2.addElement("Sparkesykkel",1.0,"stk",false);

        Assertions.assertTrue(l2.getElement(0).getValue() == 1.0);

        l2.addElement("ballonger",10.0,"stk",false);
        l2.getElement(1).setValue(4.0);

        Assertions.assertFalse(l2.getElement(1).isShopped());
        l2.getElement(1).toggleShopped();

        Assertions.assertTrue(l2.getElement(1).getValue() == 4.0);
        Assertions.assertTrue(l2.getElement(1).isShopped());
    }
}