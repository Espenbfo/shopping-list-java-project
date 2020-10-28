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
import shoppinglist.core.MaxID;
import shoppinglist.core.Passwords;
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

    /**
     * A method to write a person to JSON
     * @param persToWrite the instance of Person to be written
     * @return true if successful
     */
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

    /**
     * Reads a Person from file
     * @param personName the name of the Person to be read
     * @return read Person
     */
    public static Person readPerson(String personName){
        try {

            String filename = personName + ".json";
            ObjectMapper mapper = new ObjectMapper();
            Person out = mapper.readValue(Paths.get(filename).toFile(), Person.class);
            System.out.println(out);
            return out;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Writes the MaxID of shoppinglists to file
     * @param id the MaxID to be written
     * @return true if successful
     */
    public static boolean writeMaxID(int id){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(Paths.get("maxID.json").toFile(), new MaxID(id));
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * reads the max id
     * @return the MaxID
     */
    public static int readMaxID(){
        try {

            ObjectMapper mapper = new ObjectMapper();
            MaxID out = mapper.readValue(Paths.get("maxID.json").toFile(), MaxID.class);
            return out.getId();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * A method to write the passwords to JSON
     * @param passwordsToWrite the instance of Passwords to write
     * @return true if successful
     */
    public static boolean writePasswords(Passwords passwordsToWrite){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(Paths.get("passwords.json").toFile(), passwordsToWrite);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Reads passwords from file
     * @return read Passwords
     */
    public static Passwords readPasswords(){
        try {

            String filename = "passwords.json";
            ObjectMapper mapper = new ObjectMapper();
            Passwords out = mapper.readValue(Paths.get(filename).toFile(), Passwords.class);
            System.out.println(out);
            return out;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}