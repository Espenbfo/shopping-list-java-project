import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import shoppinglist.core.Person;
import shoppinglist.core.ShoppingElement;
import shoppinglist.core.ShoppingList;
 

public class PersonTest extends ApplicationTest {

@BeforeEach
public void init(){

    ShoppingList s1 = new ShoppingList("Testlist 1");
    ShoppingList s2 = new ShoppingList("Testlist 2");
    ShoppingList s3 = new ShoppingList("Testlist 3");

    ShoppingElement e1 = new ShoppingElement();
    ShoppingElement e2 = new ShoppingElement();
    ShoppingElement e3 = new ShoppingElement();

    s1.addElement(e1);
    s1.addElement(e2);
    s2.addElement(e2);
    s2.addElement(e3);
    s3.addElement(e1);
    s3.addElement(e3);

    Person p1 = new Person();
    Person p2 = new Person(); 

    p1.addShoppingList(s1);
    p1.addShoppingList(s2);
    p2.addShoppingList(s2);
    p2.addShoppingList(s3);

    List<ShoppingList> shoppingLists = new ArrayList<>();
    shoppingLists.add(s1);
    shoppingLists.add(s2);
    shoppingLists.add(s3);
}

@Test
public void testPerson(){
    Person p3 = new Person("Benedicte");
    p1.setUserName("Benedicte");
    assertTrue(p3.equals(p1));

    assertTrue(p2.getShoppingLists().contains(s2));
    p2.removeShoppingList(0);
    assertFalse(p2.getShoppingLists().contains(s2));
    p2.addShoppingList(s2);
    assertTrue(p2.getShoppingLists().contains(s2));
}

@Test 
public void testPersonListRelation(){

for (ShoppingList shoppingList : p1.getShoppingLists()) {
    assertTrue(shoppingList.getPersonList().contains(p1));
}

}

@Test 
public void testListPersonRelation(){

List<ShoppingList> tmpP1 = new ArrayList<>();
List<ShoppingList> tmpP2 = new ArrayList<>();

for (ShoppingList shoppingList : shoppingLists){
    if (shoppingList.getPersonList().contains(p1)){
        tmpP1.add(shoppingList);
    }
}


for (ShoppingList shoppingList : shoppingLists){
    if (shoppingList.getPersonList().contains(p2)){
        tmpP2.add(shoppingList);
    }
}

assertEquals(tmpP1, p1.getShoppingLists());
assertEquals(tmpP2, p2.getShoppingLists());
}


}

