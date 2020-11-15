package shoppinglist.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shoppinglist.core.*;
import shoppinglist.storage.FileHandler;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileNotFoundException;

public class PersistencyTest {

  private static ShoppingList l;
  private static ShoppingList m;

  @BeforeAll
  public static void init() {
    l = new ShoppingList();
    l.setTitle("Andedam");
    l.addElement("kaal", 34, "kg");
    l.addElement("loek", 3, "stk");
    l.addElement("And", 43, "unger");

    m = new ShoppingList();
    m.setId(-2);
    m.setTitle("Butikken");
    m.addElement("godteri", 47, "tonn");
  }

  @Test
  public void testWriteReadfile() {
    Assertions.assertTrue(FileHandler.writeFile(l));
    ShoppingList tl = FileHandler.readFile(l.getId());
    for (int i = 0; i < l.getElementList().size(); i++) {
      Assertions.assertTrue(l.getElement(i).equals(tl.getElement(i)));
    }
    Assertions.assertTrue(tl.equals(l));
  }

  @Test
  public void testReadUnwrittenFile() {
    Assertions.assertNull(FileHandler.readFile(m.getId()));
  }

  @Test
  public void testWriteReadPerson() {
    Assertions.assertTrue(FileHandler.writePerson(new Person("TestIndivid")));
    Assertions.assertFalse(FileHandler.writePerson(new Person("@<>/7.json")));
    Person p = FileHandler.readPerson("TestIndivid");
    Assertions.assertTrue(p.equals(new Person("TestIndivid")));
    
  }

  @Test
  public void testReadNotWrittenPerson(){
    Person p2 = FileHandler.readPerson("@<>/7.json");
    Assertions.assertNull(p2);
  }

  @Test
  public void testWriteReadMaxId() {
    Assertions.assertTrue(FileHandler.writeMaxId(4));
    Assertions.assertTrue(FileHandler.readMaxId() == 4);
  }

  @Test
  public void testWriteReadPasswords() {
    Passwords p = new Passwords();
    Person testPerson = new Person("testperson");
    p.setPassword(testPerson,"testpassord");
    String hashedPassword = p.getPassword(testPerson);

    Assertions.assertTrue(FileHandler.writePasswords(p));

    Assertions.assertTrue(FileHandler.readPasswords().getPassword(testPerson).equals(hashedPassword));

    Person notRegisteredPerson = new Person("testpersonUtenPassord");
    Assertions.assertNull(FileHandler.readPasswords().getPassword(notRegisteredPerson));

    File fileToDelte = new File("./shoppinglists/passwords.json");
    fileToDelte.delete();

    try {
      FileHandler.readPasswords().getPassword(testPerson);
      Assertions.fail();
    } catch (NullPointerException e) {

    }


    
  }

}
