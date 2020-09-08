package shoppinglist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.FileReader;

class FileHandler{

    public ShoppingList readFile(String fileName){
        BufferedReader BFR;
        ArrayList<ShoppingElement> ShoppingElementList = new ArrayList<ShoppingElement>();
        ShoppingList out;
        try{
            BFR = new BufferedReader(new FileReader(fileName));
            String line = BFR.readLine();
            String[] linearray = line.split(" ");
            ShoppingElementList.add(new ShoppingElement(linearray[0], Double.parseDouble(linearray[1]),linearray[2]));
            BFR.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        out = new ShoppingList(ShoppingElementList);
        return out;
    }

    public boolean writeFile(ShoppingList listToWrite){
        BufferedWriter BFW = new BufferedWriter(ShoppingList.getId());
        return false;
    }

}