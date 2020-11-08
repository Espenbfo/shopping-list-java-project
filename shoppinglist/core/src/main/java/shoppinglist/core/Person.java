package shoppinglist.core;

import java.util.ArrayList;

import shoppinglist.core.Passwords;

public class Person {

  /**
   * The alias of the Person.
   */
  private String userName;

  /**
   * The salt for hashing this persons password.
   */
  private byte[] salt;


  /**
   * The ShoppingLists the Person participates in.
   */
  private ArrayList<Integer> shoppingLists;


  /**
   * Constructor for already existing person with shoppinglists.
   *
   * @param userName      the name of the user
   * @param shoppingLists the shoppinglists this Person has made formerly
   */
  public Person(final String userName, final ArrayList<Integer> shoppingLists) {
    this.userName = userName;
    this.shoppingLists = shoppingLists;
    randomSalt();
  }

  /**
   * a constructor for a new Person who has not created any shoppinglists formerly.
   *
   * @param userName the name of the user
   */
  public Person(String userName) {
    this.userName = userName;
    this.shoppingLists = new ArrayList<Integer>();
    randomSalt();
  }

  /**
   * Constructor for already existing person with shoppinglists and salt.
   *
   * @param userName      the name of the user
   * @param shoppingLists the shoppinglists this Person has made formerly
   * @param salt          the salt for hashing this persons password
   */
  public Person(final String userName, final ArrayList<Integer> shoppingLists, final byte[] salt) {
    this(userName, shoppingLists);
    this.salt = salt;
  }

  /**
   * a constructor for a new Person who has not created any shoppinglists formerly.
   *
   * @param userName the name of the user
   */
  public Person() {
    this.userName = "";
    this.shoppingLists = new ArrayList<Integer>();
    randomSalt();
  }


  /**
   * Gets the username.
   *
   * @return the username
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Sets the username.
   *
   * @param newName new name
   */
  public void setUserName(final String newName) {
    userName = newName;
  }

  /**
   * Gets the salt.
   *
   * @return the salt
   */
  public byte[] getSalt() {
    return salt;
  }

  /**
   * Sets the salt.
   *
   * @param salt new salt
   */
  public void setSalt(final byte[] salt) {
    this.salt = salt;
  }


  /**
   * creates a random salt.
   */

  public void randomSalt() {
    this.salt = Passwords.randomSalt();
  }

  /**
   * Gets the shoppinglist at an index.
   *
   * @param index the index
   * @return the shoppinlist at the specified index
   */
  public Integer getShoppingList(final int index) {
    return shoppingLists.get(index);
  }

  /**
   * Sets the shoppinglists.
   *
   * @param shoppingLists the new shoppingLists
   */
  public void setShoppingLists(final ArrayList<Integer> shoppingLists) {
    this.shoppingLists = shoppingLists;
  }

  /**
   * Gets the shoppinglist by its id.
   *
   * @param id the id
   * @return the shoppinglist which has the specified id
   */
  public Integer getShoppingListById(final int id) {
    for (Integer i : shoppingLists) {
      if (i == id) {
        return i;
      }
    }
    return null;
  }

  /**
   * get list of shoppingLists.
   *
   * @return list of shoppingLists
   */
  public ArrayList<Integer> getShoppingLists() {
    return shoppingLists;
  }

  /**
   * remove a shoppinglist associated with this person.
   *
   * @param index the index of the shoppinglist you want to remove
   */
  public void removeShoppingList(final int index) {
    shoppingLists.remove(index);
  }

  /**
   * remove a shoppinglist associated with this person.
   *
   * @param id id of the shoppinglist you want to remove
   */
  public void removeShoppingListById(final int id) {
    shoppingLists.removeIf(shoppingList -> (shoppingList == id));
  }

  /**
   * adds a shoppinglist to the list of shoppinglists.
   *
   * @param i the ID of the shoppinglist
   */
  public void addShoppingList(final Integer i) {
    shoppingLists.add(i);
  }

  /**
   * adds a shoppinglist to the list of shopppinglists.
   *
   * @param l the shoppinglist which is to be added to the list of shoppinglists
   */
  public void addShoppingList(final ShoppingList l) {
    shoppingLists.add(l.getId());
  }

  /**
   * Checks if instance equals another.
   *
   * @return true if equal, false else
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!o.getClass().equals(this.getClass())) {
      return false;
    }
    Person p = (Person) o;
    if (!getUserName().equals(p.getUserName())) {
      return false;
    }
    if (getShoppingLists().size() != p.getShoppingLists().size()) {
      return false;
    }
    for (int i = 0; i < getShoppingLists().size(); i++) {
      if (!getShoppingList(i).equals(p.getShoppingList(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * to string of the name and number of lists this Person has.
   *
   * @return the name of number of lists of this person
   */
  @Override
  public String toString() {
    return userName + " " + shoppingLists.size();
  }

  @Override
  public int hashCode() {
    return 2;
  }

}
