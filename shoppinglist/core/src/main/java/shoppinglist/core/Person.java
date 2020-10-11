package shoppinglist.core;

import java.util.ArrayList;

public class Person {
    private String userName;
    private ArrayList<Integer> shoppingLists;


    /**
     * Constructor for already existing person with shoppinglists
     * @param userName the name of the user
     * @param shoppingLists the shoppinglists this Person has made formerly
     */
    public Person(String userName, ArrayList<Integer> shoppingLists){
        this.userName = userName;
        this.shoppingLists = shoppingLists;
    }

    /**
     * a constructor for a new Person who has not created any shoppinglists formerly
     * @param userName the name of the user
     */
    public Person(String userName){
        this.userName = userName;
        this.shoppingLists = new ArrayList<Integer>();
    }

    /**
     * a constructor for a new Person who has not created any shoppinglists formerly
     * @param userName the name of the user
     */
    public Person(){
        this.userName = "";
        this.shoppingLists = new ArrayList<Integer>();
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
    public Integer getShoppingList(int index) {
        return shoppingLists.get(index);
    }

    public void setShoppingLists(ArrayList<Integer> shoppingLists){this.shoppingLists = shoppingLists;}

    /**
     * Gets the shoppinglist by its id
     * @param id the id
     * @return the shoppinglist which has the specified id
     */
    public Integer getShoppingListById(int id){
        for(Integer i:shoppingLists){
            if(i == id) return i;
        }
        return null;
    }

    /**
     * get list of shoppingLists
     * @return list of shoppingLists
     */
    public ArrayList<Integer> getShoppingLists() {
        return shoppingLists;
    }

    /**
     * remove a shoppinglist associated with this person
     * @param index the index of the shoppinglist you want to remove
     */
    public void removeShoppingList(int index){
        shoppingLists.remove(index);
    }

    /**
     * remova a shoppinglist associated with this person
     * @param id id of the shoppinglist you want to remove
     */
    public void removeShoppingListById(int id){
        shoppingLists.removeIf(shoppingList -> (shoppingList == id));
    }

    /**
     * adds a shoppinglist to the list of shopppinglists
     * @param l the shoppinglist which is to be added to the list of shoppinglists
     */
    public void addShoppingList(Integer i){
        shoppingLists.add(i);
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

        if(getUserName().equals("Benedicte")) System.out.println("hie");
        Person p = (Person) o;
        if (!getUserName().equals(p.getUserName())) {
            return false;
        }
        if(getUserName().equals("Benedicte")) System.out.println("he" + getShoppingLists().size() +" " + p.getShoppingLists().size());

        if (getShoppingLists().size() != p.getShoppingLists().size()) {
            return false;
        }
        if(getUserName().equals("Benedicte")) System.out.println("h");
        for (int i = 0; i < getShoppingLists().size(); i++) {
            if (!getShoppingList(i).equals(p.getShoppingList(i))) return false;
        }



        return true;
    }

    /**
     * to string of the name and number of lists this Person has
     * @return the name of number of lists of this person
     */
    @Override
    public String toString(){
        return userName + " " + shoppingLists.size();
    }

    @Override
    public int hashCode() {
        return 2;
    }

}
