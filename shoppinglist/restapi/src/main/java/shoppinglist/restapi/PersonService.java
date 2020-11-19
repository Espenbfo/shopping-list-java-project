package shoppinglist.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import shoppinglist.core.Passwords;
import shoppinglist.core.Person;
import shoppinglist.core.ShoppingList;
import shoppinglist.restapi.PersonResource;
import shoppinglist.storage.FileHandler;

@Path(PersonService.PERSON_SERVICE_PATH)
public class PersonService {

  /**
   * the service path for the server.
   */
  public static final String PERSON_SERVICE_PATH = "Persons";

  /**
   * Maps objects to json.
   */
  private static final ObjectMapper mapper = new ObjectMapper();
  /*
  @Inject
  private Person person;
*/

  /**
   * not used.
   *
   * @return
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson() {
    return new Person();
  }

  /**
   * Recieved get for person.
   *
   *  @return person with username username
   */
  @GET
  @Path("/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson(@PathParam("username") String username) {
    return FileHandler.readPerson(username);
  }


  /**
   * Recieved Put for Person.
   *
   * @return whether the person was saved or not
   */
  @PUT
  @Path("/{username}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public int addPerson(final Person person) {
    return FileHandler.writePerson(person) ? 1 : 0;
  }

  /**
   * Recieved Get for shoppinglist.
   *
   * @param id the user-id
   * @return the shoppinglist with id id
   */
  @GET
  @Path("/ShoppingLists/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public ShoppingList getShoppingList(@PathParam("id") int id) {
    ShoppingList shoppinglist = FileHandler.readFile(id);
    return shoppinglist;
  }

  /**
   * Recieved Put for shoppinglist.
   *
   * @param shoppinglist the shoppinglist to save
   */
  @PUT
  @Path("/ShoppingLists/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public int addShoppingList(final ShoppingList shoppinglist) {
    //Gets the id of the consumed shoppinglist
    int newId = shoppinglist.getId();

    //If the newID is -1, it means the clint sent in a completely new list
    if (newId == -1) {
      //Gets the current max ID
      newId = FileHandler.readMaxId();

      //Increase it by 1 to ensure the new list has a unique id
      newId++;

      //Set the new id to the list
      shoppinglist.setId(newId);

      //Write the new max ID to file
      FileHandler.writeMaxId(newId);
    } else {
      //The list prevously saved to file.
      ShoppingList oldList = FileHandler.readFile(newId);

      if (oldList != null) {
        //The users of the list, seperated by comma.
        List<String> peopleNames = shoppinglist.getPersonList();

        //The users that are no longer on the list must be removed.
        List<String> toBeRemoved = new ArrayList<String>();

        //Find the users that no longer are on the list
        for (String p : oldList.getPersonList()) {
          try {
            if (!peopleNames.contains(p)) {
              //Reads the person
              Person person = FileHandler.readPerson(p);

              //Removes the shoppinglist, as they no longer are guest owners of it
              person.removeShoppingListById(newId);

              //Save the person to file
              FileHandler.writePerson(person);

              //Add them to be removed from the shoppinglist later.
              toBeRemoved.add(person.getUserName());
            }
          } catch (Exception ex) {
            System.out.println(ex);
          }
        }

        //remove all users in toBeRemoved from the shoppingList
        for (String username : toBeRemoved) {
          shoppinglist.removePerson(username);
        }
      }
    }

    //Adds the shoppinglist to all new guest owners
    for (String x : shoppinglist.getPersonList()) {
      Person aperson = FileHandler.readPerson(x);

      //Checks if the person exists and is not already a part of the list.
      if (aperson != null && !aperson.getShoppingLists().contains(newId)) {
        //Add the shoppinglist
        aperson.addShoppingList(newId);

        //Save the person
        FileHandler.writePerson(aperson);
      }
    }

    //Save the shoppinglist
    FileHandler.writeFile(shoppinglist);

    //returns the new ID of the shoppinglist
    return newId;
  }

}
