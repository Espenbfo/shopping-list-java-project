package shoppinglist.core;

import java.util.ArrayList;

public class Person {
    private String userName;
    private ArrayList<ShoppingList> shoppingLists;


    /**
     * Constructor for already existing person with shoppinglists
     * @param userName the name of the user
     * @param shoppingLists the shoppinglists this Person has made formerly
     */
    Person(String userName, ArrayList<ShoppingList> shoppingLists){
        this.userName = userName;
        this.shoppingLists = shoppingLists;
    }

    /**
     * a constructor for a new Person who has not created any shoppinglists formerly
     * @param userName the name of the user
     */
    Person(String userName){
        this.userName = userName;
        this.shoppingLists = new ArrayList<ShoppingList>();
    }


    /**
     * Gets the username
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username
     * @param newName new name
     */
    public void setUserName(String newName) {
        userName = newName;
    }

    /**
     * Gets the shoppinglist at an index
     * @param index the index
     * @return the shoppinlist at the specified index
     */
    public ShoppingList getShoppingList(int index) {
        return shoppingLists.get(index);
    }

    /**
     * get list of shoppingLists
     * @return list of shoppingLists
     */
    public ArrayList<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    /**
     * get list of shoppinglist ids
     * @return integer list of IDs
     */
    ArrayList<Integer> getIDList() {
        ArrayList<Integer> l =  new ArrayList<Integer>();
        for (ShoppingList s:getShoppingLists()) {
            l.add(s.getId());
        }
        return l;
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

        Person p = (Person) o;

        if (!getUserName().equals(p.getUserName())) {
            return false;
        }

        if (getShoppingLists().size() != p.getShoppingLists().size()) {
            return false;
        }

        for (int i = 0; i < getShoppingLists().size(); i++) {
            if (!getShoppingList(i).equals(p.getShoppingList(i))) return false;
        }



        return true;
    }
}
