package shoppinglist.core;

import java.util.ArrayList;
import java.lang.Math;
public class ShoppingList {
    String title;
    int id;
    static int currentMaxID = 0;
    ArrayList<Person> personList;
    ArrayList<ShoppingElement> elementList;

    public ShoppingList() {
        currentMaxID += 1;
        id = currentMaxID;
        this.elementList = new ArrayList<ShoppingElement>();
        this.personList = new ArrayList<Person>();
        this.title = "";
    }
    
    public ShoppingList(String title) {
        this();
        this.title = title;
    }
    
    public ShoppingList(String title, int id, ArrayList<Person> personList, ArrayList<ShoppingElement> elementList) {
        this.title = title;
        this.id = id;
        this.personList = personList;
        this.elementList = elementList;
        currentMaxID = Math.max(currentMaxID,id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public int getId() {
        return id;
    }


    public ArrayList<ShoppingElement> getElementList() {
        return elementList;
    }

    public ShoppingElement getElement(int index) {
        return elementList.get(index);
    }

    public void addElement(ShoppingElement e) {
        elementList.add(e);
    }

    public void addElement(String name, double value, String measurementName,boolean allowDouble) {
        elementList.add(new ShoppingElement(name,value, measurementName,allowDouble));
    }
    public void addElement(String name, double value, String measurementName) {
        elementList.add(new ShoppingElement(name,value, measurementName,true));
    }

    public void removeElement(int index) {
        elementList.remove(index);
    }

    public void removeElement(ShoppingElement e) {
        elementList.remove(e);
    }

    public ArrayList<Person> getPersonList() {
        return personList;
    }

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

    public static void main(String[] Args) {
        ShoppingList l = new ShoppingList("Liste 1");
        l.addElement("Egg",2.0, "stk",false);
        l.addElement("Melk",1.0,"l");
        l.addElement("Ost",0.5, "kg");

        ShoppingList l2 = new ShoppingList("Liste 2");
        l2.addElement("Sparkesykkel",1.0,"stk",false);
        l2.addElement("ballonger",10.0,"stk",false);
        l2.getElement(1).setValue(4.0);
        l2.getElement(1).toggleShopped();

        System.out.println(l);
        System.out.println(l2);
    }

}
