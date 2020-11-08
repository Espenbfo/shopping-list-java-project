package shoppinglist.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shoppinglist.core.ShoppingList;
/*import shoppinglist.gui.AppController;*/


public class ShoppingListTest {

  @Test
  public void testShoppingList() {
    ShoppingList l2 = new ShoppingList("Liste 2");
    l2.addElement("Sparkesykkel", 1.0, "stk", false);

    Assertions.assertTrue(l2.getElement(0).getValue() == 1.0);

    l2.addElement("ballonger", 10.0, "stk", false);
    l2.getElement(1).setValue(4.0);

    Assertions.assertFalse(l2.getElement(1).isShopped());
    l2.getElement(1).toggleShopped();

    Assertions.assertTrue(l2.getElement(1).getValue() == 4.0);
    Assertions.assertTrue(l2.getElement(1).isShopped());
  }

  @Test
  public void testPerson() {
    ShoppingList l2 = new ShoppingList("Liste 2");
    l2.addPerson(new Person("Per"));
    l2.addPerson("Per");
    l2.addPerson("Lise");
    Assertions.assertTrue(l2.getPersonList().size() == 2);
    Assertions.assertTrue(l2.getPersonList().contains("Lise"));
  }
}
