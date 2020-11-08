package shoppinglist.core;

import java.lang.Math;
import java.util.ArrayList;


/**
 * Represents a list of ShoppingElements.
 */

public class ShoppingList {
  String title;
  int id;
  ArrayList<ShoppingElement> elementList;
  ArrayList<String> personList;
  private Person owner;
  private boolean publicList;

  /**
   * Initialize a ShoppingList with default values.
   */
  public ShoppingList() {
    id = -1;
    this.elementList = new ArrayList<ShoppingElement>();
    this.personList = new ArrayList<String>();
    this.title = "";
    this.owner = null;
    this.publicList = true;
  }

  /**
   * Initialize a ShoppingList with a title.
   *
   * @param title the title
   */
  public ShoppingList(String title) {
    this();
    this.title = title;
  }


  /**
   * Initialize a ShoppingList with a title, id, and elementList.
   *
   * @param title       the title
   * @param id          the ID
   * @param elementList the list of elements
   */
  public ShoppingList(String title, int id, ArrayList<ShoppingElement> elementList) {
    this.title = title;
    this.id = id;
    this.elementList = elementList;
    this.personList = new ArrayList<String>();
    this.owner = null;
    this.publicList = true;
  }

  /**
   * Initialize a ShoppingList with a title, id, and elementList.
   *
   * @param title       the title
   * @param id          the ID
   * @param elementList the list of elements
   */
  public ShoppingList(String title, int id, ArrayList<ShoppingElement> elementList, ArrayList<String> personList) {
    this.title = title;
    this.id = id;
    this.elementList = elementList;
    this.personList = personList;
    this.publicList = true;
    //currentMaxID = Math.max(currentMaxID,id);
  }

  /**
   * Initialize a ShoppingList with a title and owner.
   */
  public ShoppingList(String title, Person owner) {
    this(title);
    this.owner = owner;
  }


  /**
   * Sets the maxID for all shoppingLists.
   * @param id the new max ID
   */
   /* public static void setCurrentMaxID(int id) {
        currentMaxID = id;
    }*/

  /**
   * Gets the max ID for all shoppinglists.
   * @return the currentMaxID
   */
    /*public static int getCurrentMaxID() {
        return currentMaxID;
    }*/


  /**
   * Gets the title.
   *
   * @return the Title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title.
   *
   * @param newTitle the new title
   */
  public void setTitle(String newTitle) {
    title = newTitle;
  }

  /**
   * Gets the id.
   *
   * @return the Id
   */
  public int getId() {
    return id;
  }

  /**
   * set the id.
   *
   * @param id to set to
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the list of userNames
   *
   * @return an arraylist of person usernames
   */
  public ArrayList<String> getPersonList() {
    return personList;
  }

  /**
   * Sets a personlist
   *
   * @param personList the arraylist of username to be set
   */
  public void setPersonList(ArrayList<String> personList) {
    this.personList = personList;
  }

  /**
   * Adds person with username p to the arrayList
   *
   * @param p the username
   */
  public void addPerson(String p) {
    if (!personList.contains(p)) {
      personList.add(p);
    }
  }

  /**
   * Adds person p to the arrayList
   *
   * @param p the Person
   */
  public void addPerson(Person p) {
    if (!personList.contains(p.getUserName())) {
      personList.add(p.getUserName());
    }
  }

  /**
   * Remove person with a username p
   *
   * @param p the username
   */
  public void removePerson(String p) {
    if (personList.contains(p)) {
      personList.remove(p);
    }
  }

  /**
   * Gets the list of elements.
   *
   * @return the elementList
   */
  public ArrayList<ShoppingElement> getElementList() {
    return elementList;
  }

  /**
   * Sets the list of elements.
   *
   * @param elementList the list to set to
   */
  public void setElementList(ArrayList<ShoppingElement> elementList) {
    this.elementList = elementList;
  }

  /**
   * Gets the element at an index.
   *
   * @param index the index
   * @return the element at the specified index
   */
  public ShoppingElement getElement(int index) {
    return elementList.get(index);
  }


  /**
   * Adds an element to the end of the elementList.
   *
   * @param e the element to be added
   */
  public void addElement(ShoppingElement e) {
    elementList.add(e);
  }

  /**
   * Adds a new element with the specified values.
   *
   * @param name            the name
   * @param value           the value
   * @param measurementName the measurement name
   * @param allowDouble     whether the element can be represented by non-integers
   */
  public void addElement(String name, double value, String measurementName, boolean allowDouble) {
    elementList.add(new ShoppingElement(name, value, measurementName, allowDouble));
  }

  /**
   * Adds a new element with the specified values.
   *
   * @param name            the name
   * @param value           the value
   * @param measurementName the measurement name
   */
  public void addElement(String name, double value, String measurementName) {
    elementList.add(new ShoppingElement(name, value, measurementName, true));
  }

  /**
   * Removes the element at the specified index.
   *
   * @param index the index
   */
  public void removeElement(int index) {
    elementList.remove(index);
  }

  /**
   * Removes the specified element.
   *
   * @param e the element to be removed
   */
  public void removeElement(ShoppingElement e) {
    elementList.remove(e);
  }

  /**
   * Sets owner to given person
   *
   * @param owner the person to be set as owner
   */
  public void setOwner(Person owner) {
    this.owner = owner;
  }

  /**
   * Get the owner of the list
   */
  public Person getOwner() {
    return this.owner;
  }

  /**
   *
   */
  public boolean getPublicList() {
    return publicList;
  }

  /**
   *
   */
  public void setPublicList(boolean listIsPublic) {
    this.publicList = listIsPublic;
  }

  /**
   * Gets a string representation of the object.
   *
   * @return a string with basic information about the instance
   */
  public String toString() {
    String elementString = "";
    for (ShoppingElement e : getElementList()) {
      elementString += "\n\n";
      elementString += e.toString();
    }
    return "---------------------\n"
            + "tittel: " + getTitle() + '\n'
            + "ID: " + getId() + '\n'
            + "elements:\n" + elementString;
  }

  /**
   * Checks if instance equals another.
   *
   * @return true if equal, false else
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (!o.getClass().equals(this.getClass())) {
      return false;
    }
    ShoppingList e = (ShoppingList) o;

    if (!getTitle().equals(e.getTitle())) {
      return false;
    }

    if (getId() != e.getId()) {
      return false;
    }

    if (getElementList().size() != e.getElementList().size()) {
      return false;
    }

    for (int i = 0; i < getElementList().size(); i++) {
      if (!getElement(i).equals(e.getElement(i))) {
       return false;
      }
    }


    return true;
  }

  @Override
  public int hashCode() {
    return 4;
  }
}

