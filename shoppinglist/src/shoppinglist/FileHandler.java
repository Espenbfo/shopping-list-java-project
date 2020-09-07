package shoppinglist;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

class FileHandler{
 
    public shoppingList readFile(String fileName){
        BufferedReader BFR = new BufferedReader(fileName);
        Arraylist<ShoppingElement> ShoppingElementList = new ArrayList<ShoppingElement>();
        shoppingList out;
        try{
            BFR.open();
            BFR.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public boolean writeFile(shoppingList listToWrite){
        BufferedWriter BFW = new BufferedWriter(shoppingList.getId());
        return false;
    }

}