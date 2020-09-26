package shoppinglist.core;

import java.util.ArrayList;

public class Person {
    private String userName;
    private ArrayList<ShoppingList> shoppingLists;


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


    public ArrayList<ShoppingList> getShoppingLists() {
        return shoppingLists;
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
