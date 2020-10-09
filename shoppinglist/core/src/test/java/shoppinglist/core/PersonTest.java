package shoppinglist.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

ShoppingList s1;
ShoppingList s2;
ShoppingList s3;

ShoppingElement e1;
ShoppingElement e2;
ShoppingElement e3;

Person p1;
Person p2;

List<ShoppingList> shoppingLists;


@BeforeEach
public void init(){

    
    s1 = new ShoppingList("Testlist 1");
    s2 = new ShoppingList("Testlist 2");
    s3 = new ShoppingList("Testlist 3");

    e1 = new ShoppingElement();
    e2 = new ShoppingElement();
    e3 = new ShoppingElement();

    s1.addElement(e1);
    s1.addElement(e2);
    s2.addElement(e2);
    s2.addElement(e3);
    s3.addElement(e1);
    s3.addElement(e3);

    p1 = new Person("p1");
    p2 = new Person("p2"); 

    p1.addShoppingList(s1);
    p1.addShoppingList(s2);
    p2.addShoppingList(s2);
    p2.addShoppingList(s3);

    shoppingLists = new ArrayList<>();
    shoppingLists.add(s1);
    shoppingLists.add(s2);
    shoppingLists.add(s3);
}

@Test
public void testPerson(){
    Person p3 = new Person("Benedicte");
    p1.setUserName("Benedicte");
    assertTrue(p3.getUserName().equals(p1.getUserName()));

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

ArrayList<ShoppingList> tmpP1 = new ArrayList<>();
ArrayList<ShoppingList> tmpP2 = new ArrayList<>();

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

