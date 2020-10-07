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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Paths;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileHandler{
    /**
     * This is a method to read the saved shoppinglists
     * @param id the id of the ShoppingList that should be read
     * @return the Shoppinglist which has been read
     */
    public static ShoppingList readFile(int id){
        try {
            String filename = id + ".json";
            ObjectMapper mapper = new ObjectMapper();
            ShoppingList out = mapper.readValue(Paths.get(id + ".json").toFile(), ShoppingList.class);
            return out;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * a method to save a Shoppinglist as a file
     * @param listToWrite the shoppinglist which should be written to a file
     * @return true if the file was written, false if it was not
     */
    public static boolean writeFile(ShoppingList listToWrite){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(Paths.get(listToWrite.getId() + ".json").toFile(), listToWrite);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean writePerson(Person persToWrite){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(Paths.get(persToWrite.getUserName() + ".json").toFile(), persToWrite);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Person readFile(String personName){
        try {
            String filename = personName + ".json";
            ObjectMapper mapper = new ObjectMapper();
            Person out = mapper.readValue(Paths.get(personName + ".json").toFile(), Person.class);
            return out;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ShoppingList atestlist = new ShoppingList("Denne handlelisten");
        atestlist.addElement("mjolk", 33, "L");
        atestlist.addElement("Smør", 8, "Kg");
        FileHandler ahandler = new FileHandler();
        ahandler.writeFile(atestlist);
        
    }



}