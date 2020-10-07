package shoppinglist.core;

import java.util.ArrayList;
import java.lang.Math;

/**
 * Represents a list of ShoppingElements.
 */

public class ShoppingList {
    String title;
    int id;
    static int currentMaxID = 0;
    ArrayList<Person> personList;
    ArrayList<ShoppingElement> elementList;

    /**
     * Initialize a ShoppingList with default values.
     */
    public ShoppingList() {
        currentMaxID += 1;
        id = currentMaxID;
        this.elementList = new ArrayList<ShoppingElement>();
        this.personList = new ArrayList<Person>();
        this.title = "";
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
     * Initialize a ShoppingList with a title, id, personList, and elementList.
     *
     * @param title the title
     * @param id the ID
     * @param personList the list of people
     * @param elementList the list of elements
     */
    public ShoppingList(String title, int id, ArrayList<Person> personList, ArrayList<ShoppingElement> elementList) {
        this.title = title;
        this.id = id;
        this.personList = personList;
        this.elementList = elementList;
        currentMaxID = Math.max(currentMaxID,id);
    }

    /**
     * Gets the title
     * @return the Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     * @param newTitle the new title
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    /**
     * Gets the id
     * @return the Id
     */
    public int getId() {
        return id;
    }

    /**
     * set the id
     * @param id to set to
     */
    public void setId(int id){this.id = id;}

    /**
     * Gets the list of elements
     * @return the elementList
     */
    public ArrayList<ShoppingElement> getElementList() {
        return elementList;
    }

    /**
     * Sets the list of elements
     * @param elementList the list to set to
     */
    public void setElementList(ArrayList<ShoppingElement> elementList){this.elementList = elementList;}
    /**
     * Gets the element at an index
     * @param index the index
     * @return the element at the specified index
     */
    public ShoppingElement getElement(int index) {
        return elementList.get(index);
    }

    /**
     * Gets the person at an index
     * @param index the index
     * @return the person at the specified index
     */
    public Person getPerson(int index) {
        return getPersonList().get(index);
    }

    /**
     * add the person to PersonList
     * @param p the Person to add
     *
     */
    public void addPerson(Person p) {
        if (!p.getShoppingLists().contains(this)) {
            p.addShoppingList(this);
        }
        getPersonList().add(p);
    }

    /**
     * Adds an element to the end of the elementList
     * @param e the element to be added
     */
    public void addElement(ShoppingElement e) {
        elementList.add(e);
    }

    /**
     * Adds a new element with the specified values
     * @param name the name
     * @param value the value
     * @param measurementName the measurement name
     * @param allowDouble whether the element can be represented by non-integers
     */
    public void addElement(String name, double value, String measurementName,boolean allowDouble) {
        elementList.add(new ShoppingElement(name,value, measurementName,allowDouble));
    }

    /**
     * Adds a new element with the specified values
     * @param name the name
     * @param value the value
     * @param measurementName the measurement name
     */
    public void addElement(String name, double value, String measurementName) {
        elementList.add(new ShoppingElement(name,value, measurementName,true));
    }

    /**
     * Removes the element at the specified index
     * @param index the index
     */
    public void removeElement(int index) {
        elementList.remove(index);
    }

    /**
     * Removes the specified element
     * @param e the element to be removed
     */
    public void removeElement(ShoppingElement e) {
        elementList.remove(e);
    }

    /**
     * Gets the personList
     * @return the personList
     */
    public ArrayList<Person> getPersonList() {
        return personList;
    }

    /**
     * set the personlist
     * @param personList the personlist to set to
     */
    public void setPersonList(ArrayList<Person> personList){this.personList = personList;}

    /**
     * Gets a description of the list, and all the elements in it
     *
     * @return a String representation of the list
     */
    public String toString() {
        String elementString = "";
        for (ShoppingElement e : getElementList()) {
            elementString += "\n\n";
            elementString += e.toString();
        }
        return "---------------------\n" +
                "tittel: " + getTitle()  +  '\n' +
                "ID: " + getId()  +  '\n' +
                "elements:\n" + elementString;
    }

    /**
     * Checks if instance equals another
     *
     * @return true if equal, false else
     */
    @Override
    public boolean equals(Object o) {
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
            if (!getElement(i).equals(e.getElement(i))) return false;
        }

        if (getPersonList().size() != e.getPersonList().size()) {
            return false;
        }

        for (int i = 0; i < getPersonList().size(); i++) {
            if (!getPerson(i).equals(e.getPerson(i))) return false;
        }


        return true;
    }
}
