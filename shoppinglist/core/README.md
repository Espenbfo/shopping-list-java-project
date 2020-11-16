# Kjernemodulen (core)

Denne modulen inneholder domene- og persistenslaget

## Domenelaget
I domenelaget ligger logikken knyttet til applikasjonen. 
Her ligger logikk for:
- Person
- Password
- MeasurementType
- ShoppingList
- ShoppingElement


## Persistenslaget 
Persistenslaget inneholder logikk for lagring med JSON. Det kan da produseres tekstfiler med et ShoppingList-objekt, med tilhørende Person (evt. flere) og ShoppingElement(s). Det er også implementert lagring av personer med tilhørende data.
Passordinformasjon lagres også på JSON-format.
Logikken støtter også opphenting av slike filer. 

## Klassediagram 
@startuml
class ShoppingList {
    -String title
    -int id
    -ArrayList<ShoppingElement> elementList
    -ArrayList<Person> personList
    -Person owner
    -boolean publicList
    
    
    + ShoppingList()
    + ShoppingList(String title)
    + ShoppingList(String title, int id, ArrayList<ShoppingElement> elementList)
    + ShoppingList(String title, int id, ArrayList<String> personList)
    + ShoppingList(String title, Person owner)
    + String getTitle()
    + void setTitle(String newTitle)
    + int getId()
    + void setId(int id)
    + ArrayList<String> getPersonList()
    + void setPersonList(ArrayList<String> personList)
    + void addPerson(String p)
    + void addPerson(Person p)
    + void removePerson(String p)
    + ArrayList<ShoppingElement> getElementList()
    + void setElementList(ArrayList<ShoppingElement> elementList)
    + ShoppingElement getElement(int index)
    + void addElement(ShoppingElement e)
    + void addElement(String name, double value, String measurementName, boolean allowDouble)
    + void addElement(String name, double value, String measurementName)
    + void removeElement(int index)
    + void removeElement(ShoppingElement e)
    + void setOwner(Person owner)
    + Person getOwner()
    + boolean getPublicList()
    + void setPublicList(boolean listIsPublic)
    + String toString()
    + boolean equals(Object o)
    + int hashCode()
}

class ShoppingElement {
    - String name
    - MeasurementType measurementType
    - boolean shopped
    
    + ShoppingElement()
    + ShoppingElement(String name, double value, String measurementName, boolean allowDouble)
    + ShoppingElement(String name, double value, String measurementName)
    + ShoppingElement(String name, MeasurementType m, boolean shopped)
    + double getValue()
    + void setValue(double newValue)
    + void changeValue(double difference)
    + void toggleShopped()
    + boolean isShopped()
    + void setShopped(boolean shopped)
    + String getName()
    + void setName(String newName)
    + String getMeasurementName()
    + MeasurementType getMeasurementType()
    + void setMeasurementType(MeasurementType measurementType)
    + String toString()
    + boolean equals(Object o)
    + int hashCode()
    
}

class Person {
    - String userName
    - byte[] salt
    - ArrayList<Integer> shoppingLists
    
    + Person(final String userName, final ArrayList<Integer> shoppingLists)
    + Person(String userName)
    + Person(final String userName, final ArrayList<Integer> shoppingLists, final byte[] salt)
    + Person()
    + String getUserName()
    + void setUserName(final String newName)
    + byte[] getSalt()
    + void setSalt(final byte[] salt)
    + void randomSalt()
    + Integer getShoppingList(final int index)
    + void setShoppingLists(final ArrayList<Integer> shoppingLists)
    + Integer getShoppingListById(final int id)
    + ArrayList<Integer> getShoppingLists()
    + void removeShoppingList(final int index)
    + void removeShoppingListById(final int id)
    + void addShoppingList(final Integer i)
    + void addShoppingList(final ShoppingList l)
    + boolean equals(Object o)
    + String toString()
    + int hashCode()
}

class MeasurementType {
    - boolean allowDouble
    - String baseName
    - double value
    
    + MeasurementType()
    + MeasurementType(final String baseName, final double value, final boolean allowDouble)
    + MeasurementType(final String baseName, final double value)
    + double getValue()
    + void setValue(final double newValue)
    + void changeValue(final double difference)
    + void setBaseName(final String name)
    + String getBaseName()
    + boolean allowDouble()
    + boolean equals(final Object o)
    + int hashCode()
}

class Password {
    - HashMap<String, String> hashes 
    - static int hashLength
    - static int hashIterations
    - static int saltLength
    
    + Passwords()
    + Passwords(HashMap<String, String> hashes)
    + void setHashes(HashMap<String, String> hashes)
    + HashMap<String, String> getHashes()
    + void setPassword(Person p, String password)
    + String getPassword(Person p)
    + boolean checkPassword(String userName, String password, byte[] salt)
    + boolean checkPassword(Person p, String password)
    + static String hash(String password, byte[] salt)
    + static byte[] randomSalt()
}

class MaxId {
    - int id
    
    + MaxID()
    + MaxID(final int id)
    + void setId(final int id)
    + int getId()
}

class Client {
    - static Person currentPerson
    - static Passwords passwords
    
    - Client()
    + static Passwords getPasswords()
    + static void setPasswords(Passwords passwords)
    + static void setCurrentPerson(final Person p)
    + static void setCurrentPerson(final String p)
    + static Person getCurrentPerson()
    
}

Person "*"--"*" ShoppingList
ShoppingList "1"--"*" ShoppingElement
Person "1"--"1" Passwords
ShoppingElement "1"--"1" MeasurementType


@enduml