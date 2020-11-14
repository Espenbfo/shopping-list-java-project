package shoppinglist.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import shoppinglist.core.MaxId;
import shoppinglist.core.MeasurementType;
import shoppinglist.core.Passwords;
import shoppinglist.core.Person;
import shoppinglist.core.ShoppingElement;
import shoppinglist.core.ShoppingList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileHandler {
  /**
   * This is a method to read the saved shoppinglists.
   *
   * @param id the id of the ShoppingList that should be read
   * @return the Shoppinglist which has been read
   */
  public static ShoppingList readFile(int id) {
    try {
      File dir = new File("./shoppinglists/");
      if (!dir.exists()) {
        dir.mkdir();
      }
      ObjectMapper mapper = new ObjectMapper();
      ShoppingList out = mapper.readValue(Paths
        .get("./shoppinglists/" + id + ".json")
        .toFile(), ShoppingList.class);
      return out;
    } catch (Exception e) {
        System.out.println("e");
    }
    return null;
  }

  /**
   * a method to save a Shoppinglist as a file.
   *
   * @param listToWrite the shoppinglist which should be written to a file
   * @return true if the file was written, false if it was not
   */
  public static boolean writeFile(ShoppingList listToWrite) {
    try {
      File dir = new File("./shoppinglists/");
      if (!dir.exists()) {
        dir.mkdir();
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(Paths
        .get("./shoppinglists/" + listToWrite.getId() + ".json")
        .toFile(), listToWrite);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * A method to write a person to JSON.
   *
   * @param persToWrite the instance of Person to be written
   * @return true if successful
   */
  public static boolean writePerson(Person persToWrite) {
    try {
      File dir = new File("./persons/");
      if (!dir.exists()) {
        dir.mkdir();
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(Paths
        .get("./persons/" 
        + persToWrite.getUserName() 
        + ".json").toFile(), persToWrite);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Reads a Person from file.
   *
   * @param personName the name of the Person to be read
   * @return read Person
   */
  public static Person readPerson(String personName) {
    try {
      File dir = new File("./persons/");
      if (!dir.exists()) {
        dir.mkdir();
      }
      String filename = "./persons/" + personName + ".json";
      ObjectMapper mapper = new ObjectMapper();
      Person out = mapper.readValue(Paths.get(filename).toFile(), Person.class);
      System.out.println(out);
      return out;
    } catch (Exception e) {

    }
    return null;
  }

  /**
   * Writes the MaxID of shoppinglists to file.
   *
   * @param id the MaxID to be written
   * @return true if successful
   */
  public static boolean writeMaxId(int id) {
    try {
      File dir = new File("./shoppinglists/");
      if (!dir.exists()) {
        dir.mkdir();
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(Paths.get("./shoppinglists/maxID.json").toFile(), new MaxId(id));
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * reads the max id.
   *
   * @return the MaxID
   */
  public static int readMaxId() {
    try {
      File dir = new File("./shoppinglists/");
      if (!dir.exists()) {
        dir.mkdir();
      }
      ObjectMapper mapper = new ObjectMapper();
      MaxId out = mapper.readValue(Paths.get("./shoppinglists/maxID.json").toFile(), MaxId.class);
      return out.getId();
    } catch (IOException e) {
      writeMaxId(0);
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1;
  }

  /**
   * A method to write the passwords to JSON.
   *
   * @param passwordsToWrite the instance of Passwords to write
   * @return true if successful
   */
  public static boolean writePasswords(Passwords passwordsToWrite) {
    try {
      File dir = new File("./shoppinglists/");
      if (!dir.exists()) {
        dir.mkdir();
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(Paths.get("./shoppinglists/passwords.json").toFile(), passwordsToWrite);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Reads passwords from file.
   *
   * @return read Passwords
   */
  public static Passwords readPasswords() {
    try {

      String filename = "./shoppinglists/passwords.json";
      ObjectMapper mapper = new ObjectMapper();
      File dir = new File("./shoppinglists/");
      if (!dir.exists()) {
        dir.mkdir();
      }
      Passwords out = mapper.readValue(Paths.get(filename).toFile(), Passwords.class);
      System.out.println(out);
      return out;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}