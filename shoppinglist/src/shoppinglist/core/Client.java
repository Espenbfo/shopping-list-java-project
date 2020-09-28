package shoppinglist.core;

public class Client {
    private static Person currentPerson;
    public static void setCurrentPerson(Person p){
        Client.currentPerson = p;
    }
    public static void setCurrentPerson(String p){
        Client.currentPerson = new Person(p);

    }
    public static Person getCurrentPerson(){
        return currentPerson;
    }

}
