package shoppinglist.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinglist.core.Person;
import shoppinglist.core.ShoppingElement;
import shoppinglist.core.ShoppingList;

public class PersonTest {

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
    public void init() {


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

        shoppingLists = new ArrayList<>();
        shoppingLists.add(s1);
        shoppingLists.add(s2);
        shoppingLists.add(s3);
    }

    @Test
    public void testName() {
        Person p3 = new Person("Benedicte");
        p1.setUserName("Benedicte");
        assertTrue(p1.getUserName().equals("Benedicte"));
        assertTrue(p3.getUserName().equals(p1.getUserName()));
    }

    @Test
    public void testShoppingListsWithID() {
        p1.addShoppingList(2);
        p1.addShoppingList(3);
        p1.addShoppingList(4);

        assertTrue(p1.getShoppingLists().contains(2));
        assertTrue(p1.getShoppingLists().contains(3));
        assertTrue(p1.getShoppingLists().contains(4));

        System.out.println(p1.getShoppingListById(2));
        //assertEquals(p1.getShoppingListById(2), 4);
    }

    @Test
    public void testShoppingListsWithShoppingList() {
        p1.addShoppingList(s1);
        p1.addShoppingList(s2);
        p1.addShoppingList(s3);
        assertTrue(p1.getShoppingLists().contains(s1.getId()));
        assertTrue(p1.getShoppingLists().contains(s2.getId()));
        assertTrue(p1.getShoppingLists().contains(s3.getId()));
    }

    @Test
    public void testEquals() {
        p1.setUserName("equals");
        p2.setUserName("equals");

        assertTrue(p1.equals(p2));

        p1.addShoppingList(1);
        p2.addShoppingList(1);

        assertTrue(p1.equals(p2));
    }

    @Test
    public void testNewPerson() {
        Person p = new Person();
        Person p3 = new Person("");
        Person p4 = new Person("", new ArrayList<Integer>());
        assertTrue(p.getUserName().equals(""));
        assertTrue(p.equals(p3));
        assertTrue(p.equals(p4));
    }
}

