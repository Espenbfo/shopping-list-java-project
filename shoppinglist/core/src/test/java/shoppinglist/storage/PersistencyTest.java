package shoppinglist.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shoppinglist.core.*;
import shoppinglist.storage.FileHandler;

public class PersistencyTest{

    @Test
    public void testStorage(){
        ShoppingList l = new ShoppingList();
        l.setTitle("Andedam");
        l.addElement("kaal",34,"kg");
        l.addElement("loek",3,"stk");
        l.addElement("And",43,"unger");
        FileHandler.writeFile(l);
        ShoppingList tl = FileHandler.readFile(l.getId());
        for(int i = 0; i < l.getElementList().size(); i++){
            Assertions.assertTrue(l.getElement(i).equals(tl.getElement(i)));
        }
        Assertions.assertTrue(tl.equals(l));
    }

}
