package shoppinglist.core;

/**
 * This class stores information between scenes in the App.
 */
final public class Client {

  private Client() {
    return;
  }

  /**
   * The Person which currently is active on this client.
   */
  private static Person currentPerson;

  /**
   * The passwords (This will be stored on the server later).
   */
  private static Passwords passwords;

  /**
   * get passwords.
   *
   * @return passwords
   */
  public static Passwords getPasswords() {
    return passwords;
  }

  /**
   * set passwords.
   *
   * @param passwords new passwords
   */
  public static void setPasswords(Passwords passwords) {
    Client.passwords = passwords;
  }

  /**
   * Change the Person that currently is active on this client.
   *
   * @param p the  Person to be the new active user
   */
  public static void setCurrentPerson(final Person p) {
    Client.currentPerson = p;
  }

  /**
   * Set current Person on this client, by a new Person class from a name.
   *
   * @param p the name of the new user
   */
  public static void setCurrentPerson(final String p) {
    Client.currentPerson = new Person(p);

  }

  /**
   * get the Person who currently uses the pc.
   *
   * @return the Person who is currently active
   */
  public static Person getCurrentPerson() {
    return currentPerson;
  }

}
