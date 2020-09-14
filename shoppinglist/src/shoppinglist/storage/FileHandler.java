package shoppinglist.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import shoppinglist.core.ShoppingList;
import shoppinglist.core.MeasurementType;
import shoppinglist.core.ShoppingElement;
import shoppinglist.core.Person;

public class FileHandler{

    public static ShoppingList readFile(int id){
        BufferedReader BFR;
        ArrayList<ShoppingElement> ShoppingElementList = new ArrayList<ShoppingElement>();
        ShoppingList out;
        String title = "";
        File inFile = new File("./"+id+".txt");
        if(!inFile.exists()) return null;
        try{
            BFR = new BufferedReader(new FileReader(inFile));
            title = BFR.readLine();
            while(BFR.ready()){
                String line = BFR.readLine();
                String[] linearray = line.split(" ");
                ShoppingElementList.add(new ShoppingElement(linearray[0], Double.parseDouble(linearray[1]),linearray[2]));
            }
            BFR.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        out = new ShoppingList(title,id,new ArrayList<Person>(),ShoppingElementList);
        return out;
    }

    public static boolean writeFile(ShoppingList listToWrite){
        BufferedWriter BFW;
        try{
            File outfil = new File("./"+listToWrite.getId()+".txt");
            if(!outfil.exists())outfil.createNewFile();
            BFW = new BufferedWriter(new FileWriter(outfil));
            BFW.write(listToWrite.getTitle());
            for(ShoppingElement x: listToWrite.getElementList()){
                BFW.newLine();
                BFW.write(x.getName() + " " + x.getValue() + " l");
            }
            BFW.flush();
            BFW.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ShoppingList atestlist = new ShoppingList("Denne handlelisten");
        atestlist.addElement("mjolk", 33, "L");
        atestlist.addElement("Smør", 8, "Kg");
        FileHandler ahandler = new FileHandler();
        ahandler.writeFile(atestlist);
        
    }

}