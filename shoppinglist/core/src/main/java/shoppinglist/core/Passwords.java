package shoppinglist.core;

import java.lang.RuntimeException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.codec.binary.Base64;
import shoppinglist.core.Person;

/**
 * This class contains a hashmap of username passwords combinations,
 * and has multiple static methods for passwords handling.
 */
public class Passwords {

  private HashMap<String, String> hashes; //User, Password
  private static int hashLength = 256;
  private static int hashIterations = 10000;
  private static int saltLength = 16;

  /**
   * Constructor for empty passwords instance.
   */
  public Passwords() {
    hashes = new HashMap<String, String>();
  }

  /**
   * Construktor of existing password instance.
   *
   * @param hashes the username-password hashmap
   */
  public Passwords(HashMap<String, String> hashes) {
    this.hashes = hashes;
  }

  /**
   * Sets all the username-password pairs.
   *
   * @param hashes the hasmap to be set
   */
  public void setHashes(HashMap<String, String> hashes) {
    this.hashes = hashes;
  }

  /**
   * Gets the username-password hashmap.
   *
   * @return the username-password hashmap
   */
  public HashMap<String, String> getHashes() {
    return this.hashes;
  }

  /**
   * Sets a username-password pair.
   *
   * @param p        the Person
   * @param password the password
   */
  public void setPassword(Person p, String password) {
    //Gets the hash
    String hashedPassword = Passwords.hash(password, p.getSalt());

    //Stores it in the hasmap
    hashes.put(p.getUserName(), hashedPassword);
  }

  /**
   * Gets a password belonging til user P.
   *
   * @param p the Person
   */
  public String getPassword(Person p) {
    return hashes.get(p.getUserName());
  }

  /**
   * Checks if a password is valid with a username.
   *
   * @param userName The username
   * @param password The password to be checked
   * @param salt     The salt to apply to the password before hashing
   * @return whether the password was correct or not
   */
  public boolean checkPassword(String userName, String password, byte[] salt) {
    //Hashes the sent in password
    String hashedPassword = Passwords.hash(password, salt);

    //Checks if the user has a password
    if (hashes.get(userName) == null) {
      return false;
    }

    //Checks if the password's hash is equal to the stored password's hash
    if (hashes.get(userName).equals(hashedPassword)) {
      return true;
    }
    return false;
  }

  /**
   * Checks the password of an instance of person.
   *
   * @param p        the Person to check
   * @param password the password to check
   * @return whether the password was correct or not
   */
  public boolean checkPassword(Person p, String password) {
    return checkPassword(p.getUserName(), password, p.getSalt());
  }

  /**
   * Hashes a password with the supplied salt.
   *
   * @param password The password to be hashed
   * @param salt     The salt to apply to the password
   * @return a String representation of the hash
   */
  public static String hash(String password, byte[] salt) {
    try {
      //Gets the Sha512 instance
      SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

      //Hashes the password with the given salt
      SecretKey key = f.generateSecret(new PBEKeySpec(
              password.toCharArray(), salt, hashIterations, hashLength));

      //Converts the SecretKey encoded hash to a String
      return Base64.encodeBase64String(key.getEncoded());
    } catch (RuntimeException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      System.out.println("Noe gikk feil med hashingen");
      return "";
    }
  }

  /**
   * Returns a randomly generated salt to be used in a Person instance.
   *
   * @return a randomly generated salt to be used in a Person instance
   */
  public static byte[] randomSalt() {
    byte[] salt = new byte[saltLength];
    SecureRandom random = new SecureRandom();
    random.nextBytes(salt);
    return salt;
  }
}