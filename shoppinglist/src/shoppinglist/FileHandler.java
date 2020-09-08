package shoppinglist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;

class FileHandler{

    public ShoppingList readFile(int id){
        BufferedReader BFR;
        ArrayList<ShoppingElement> ShoppingElementList = new ArrayList<ShoppingElement>();
        ShoppingList out;
        String title = "";
        try{
            BFR = new BufferedReader(new FileReader("/"+id+".txt"));
            title = BFR.readLine();
            String line = BFR.readLine();
            String[] linearray = line.split(" ");
            ShoppingElementList.add(new ShoppingElement(linearray[0], Double.parseDouble(linearray[1]),linearray[2]));
            BFR.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        out = new ShoppingList(title,id,new ArrayList<Person>(),ShoppingElementList);
        return out;
    }

    public boolean writeFile(ShoppingList listToWrite){
        BufferedWriter BFW;
        try{
            BFW = new BufferedWriter(new FileWriter("/"+listToWrite.getId()+".txt"));
            BFW.write(listToWrite.title);
            for(ShoppingElement x: listToWrite.getElementList()){
                BFW.newLine();
                BFW.write(x.name + " " + x.measurementType.getValue() + " " + x.measurementType.getBaseName());
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}