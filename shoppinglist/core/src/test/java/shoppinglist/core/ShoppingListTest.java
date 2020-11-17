package shoppinglist.core;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shoppinglist.core.ShoppingList;


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

    ShoppingElement e1 = new ShoppingElement("ballonger", 10.0, "stk");
    List<ShoppingElement> elementList = new ArrayList<ShoppingElement>();
    elementList.add(e1);
    List<String> personList = new ArrayList<String>();
    personList.add("Per");
    ShoppingList l3 = new ShoppingList("Liste 3", 1, elementList, personList);
    Assertions.assertTrue(l3.getElementList().contains(e1));
    l3.removeElement(e1);
    Assertions.assertFalse(l3.getElementList().contains(e1));

    Assertions.assertTrue(l3.getPersonList().contains("Per"));
    l3.removePerson("Per");
    Assertions.assertFalse(l3.getPersonList().contains("Per"));
  }
}
