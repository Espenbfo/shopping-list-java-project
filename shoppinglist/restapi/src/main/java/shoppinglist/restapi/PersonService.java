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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
   * logger for logging server issues.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

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
   * @return person with username username
   * 
   */
  @GET
  @Path("/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson(@PathParam("username") String username) {
    LOG.debug("addShoppingList({})", username);
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
    LOG.debug("addShoppingList({})", person);
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
    LOG.debug("Sub-resource for Person " + id + ": " + shoppinglist);
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
    int newId = shoppinglist.getId();
    if (newId == -1) {
      newId = FileHandler.readMaxId();
      newId++;
      shoppinglist.setId(newId);
      FileHandler.writeMaxId(newId);
    }
    else {
      //The list prevously saved to file.
      ShoppingList oldList = FileHandler.readFile(newId);

      if (oldList != null) {
        //The users of the list, seperated by comma.
        List<String> peopleNames = shoppinglist.getPersonList();

        //The users that are no longer on the list must be removed.
        List<String> toBeRemoved = new ArrayList<String>();

        for (String p : oldList.getPersonList()) {
          System.out.println(p);
          try {
            if (!peopleNames.contains(p)) {
              Person person = FileHandler.readPerson(p);
              person.removeShoppingListById(newId);
              FileHandler.writePerson(person);
              toBeRemoved.add(person.getUserName());
            }
          } catch (Exception ex) {
            System.out.println(ex);
          }
        }
        for (String username : toBeRemoved) {
          shoppinglist.removePerson(username);
        }
      }
    }
    for (String x : shoppinglist.getPersonList()) {
      Person aperson = FileHandler.readPerson(x);
      if (aperson != null && !aperson.getShoppingLists().contains(newId)) {
        aperson.addShoppingList(newId);
        FileHandler.writePerson(aperson);
      }
    }
    FileHandler.writeFile(shoppinglist);
    return newId;
  }

}
