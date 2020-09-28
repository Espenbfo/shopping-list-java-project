package shoppinglist.core;

public class Client {

    /**
     * The Person which currently is active on this client
     */
    private static Person currentPerson;

    /**
     * Change the Person that currently is active on this client
     * @param p the  Person to be the new active user
     */
    public static void setCurrentPerson(Person p){
        Client.currentPerson = p;
    }

    /**
     * Set current Person that is currently active on this client, by a new Person class from a name
     * @param p the name of the new user
     */
    public static void setCurrentPerson(String p){
        Client.currentPerson = new Person(p);

    }

    /**
     * get the Person who currently uses the pc
     * @return the Person who is currently active
     */
    public static Person getCurrentPerson(){
        return currentPerson;
    }

}
