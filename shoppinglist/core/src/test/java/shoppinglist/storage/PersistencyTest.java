package shoppinglist.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shoppinglist.core.*;
import shoppinglist.storage.FileHandler;
import org.junit.jupiter.api.BeforeAll;
import java.io.File;
import java.io.FileNotFoundException;

public class PersistencyTest{

    private static ShoppingList l;

    @BeforeAll
    public static void init(){
        l = new ShoppingList();
        l.setTitle("Andedam");
        l.addElement("kaal",34,"kg");
        l.addElement("loek",3,"stk");
        l.addElement("And",43,"unger");
    }

    @Test
    public void testWriteFile(){
        Assertions.assertTrue(FileHandler.writeFile(l));
    }
    @Test
    public void testReadfile(){
        System.out.println(l.toString());
        ShoppingList tl = FileHandler.readFile(l.getId());
        for(int i = 0; i < l.getElementList().size(); i++){
            Assertions.assertTrue(l.getElement(i).equals(tl.getElement(i)));
        }
        Assertions.assertTrue(tl.equals(l));
    }

    @Test
    public void testWriteReadPerson(){
        Assertions.assertTrue(FileHandler.writePerson(new Person("TestIndivid")));
        Assertions.assertFalse(FileHandler.writePerson(new Person("@<>/7.json")));
        Person p = FileHandler.readPerson("TestIndivid");
        Assertions.assertTrue(p.equals(new Person("TestIndivid")));
    }


    @Test
    public void testWriteReadMaxID(){

        Assertions.assertTrue(FileHandler.writeMaxID(l.getCurrentMaxID()));
        Assertions.assertTrue(FileHandler.readMaxID() == l.getCurrentMaxID());
    }



}
