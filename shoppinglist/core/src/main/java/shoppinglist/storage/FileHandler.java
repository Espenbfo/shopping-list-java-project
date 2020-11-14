package shoppinglist.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.RuntimeException;
import java.nio.file.Paths;
import java.util.ArrayList;

import shoppinglist.core.MaxID;
import shoppinglist.core.MeasurementType;
import shoppinglist.core.Passwords;
import shoppinglist.core.Person;
import shoppinglist.core.ShoppingElement;
import shoppinglist.core.ShoppingList;
import java.io.IOException;
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
        if (!dir.mkdir()) {
          System.out.println("Could not create directory");
          return null;
        }
      }
      ObjectMapper mapper = new ObjectMapper();
      ShoppingList out = mapper.readValue(Paths.get("./shoppinglists/" + id + ".json").toFile(), ShoppingList.class);
      return out;
    } catch (RuntimeException | IOException e) {
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
        if (!dir.mkdir()) {
          System.out.println("Could not create directory");
          return false;
        }
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(Paths.get("./shoppinglists/" + listToWrite.getId() + ".json").toFile(), listToWrite);
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
        if (!dir.mkdir()) {
          System.out.println("Could not create directory");
          return false;
        }
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(Paths.get("./persons/" + persToWrite.getUserName() + ".json").toFile(), persToWrite);
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
        if (!dir.mkdir()) {
          System.out.println("Could not create directory");
          return null;
        }
      }
      String filename = "./persons/" + personName + ".json";
      ObjectMapper mapper = new ObjectMapper();
      Person out = mapper.readValue(Paths.get(filename).toFile(), Person.class);
      System.out.println(out);
      return out;
    } catch (RuntimeException | IOException e) {

    }
    return null;
  }

  /**
   * Writes the MaxID of shoppinglists to file.
   *
   * @param id the MaxID to be written
   * @return true if successful
   */
  public static boolean writeMaxID(int id) {
    try {
      File dir = new File("./shoppinglists/");
      if (!dir.exists()) {
        if (!dir.mkdir()) {
          System.out.println("Could not create directory");
          return false;
        }
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(Paths.get("./shoppinglists/maxID.json").toFile(), new MaxID(id));
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
  public static int readMaxID() {
    try {
      File dir = new File("./shoppinglists/");
      if (!dir.exists()) {
        if (!dir.mkdir()) {
          System.out.println("Could not create directory");
          return -1;
        }
      }
      ObjectMapper mapper = new ObjectMapper();
      MaxID out = mapper.readValue(Paths.get("./shoppinglists/maxID.json").toFile(), MaxID.class);
      return out.getId();
    } catch (IOException e) {
      writeMaxID(0);
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
        if (!dir.mkdir()) {
          System.out.println("Could not create directory");
          return false;
        }
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
        if (!dir.mkdir()) {
          System.out.println("Could not create directory");
          return null;
        }
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