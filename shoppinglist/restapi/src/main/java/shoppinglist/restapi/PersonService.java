package shoppinglist.restapi;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.core.Person;
import shoppinglist.core.ShoppingList;
import shoppinglist.restapi.PersonResource;
import shoppinglist.storage.FileHandler;

@Path(PersonService.PERSON_SERVICE_PATH)
public class PersonService {

  /**
   * the service path for the server
   */
  public static final String PERSON_SERVICE_PATH = "Persons";
  /**
   * Arraylist for caching persons in memory, not currently used
   */
  private static ArrayList<Person> persons = new ArrayList<Person>();
  /**
   * logger for logging server issues
   */
  private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);
  /*
  @Inject
  private Person person;
*/

  /**
   * not used
   *
   * @return
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson() {
    return new Person();
  }

  /**
   * Recieved get for person
   *
   * @param username
   * @return person with username username
   */
  @GET
  @Path("/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson(@PathParam("username") String username) {
    return FileHandler.readPerson(username);
  }


  /**
   * Recieved Put for Person
   *
   * @param person
   * @return whether the person was saved or not
   */
  @PUT
  @Path("/{username}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public int addPerson(final Person person) {
    LOG.debug("addShoppingList({})", person);
    System.out.println("person");
    return FileHandler.writePerson(person) ? 1 : 0;
  }

  /**
   * Recieved Get for shoppinglist
   *
   * @param id
   * @return the shoppinglist with id id
   */
  @GET
  @Path("/ShoppingLists/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public ShoppingList getShoppingList(@PathParam("id") int id) {
    ShoppingList shoppinglist = FileHandler.readFile(id);
    LOG.debug("Sub-resource for Person " + id + ": " + shoppinglist);
    System.out.println(id);
    Person person = new Person();
    return shoppinglist;
  }


  /**
   * Recieved Put for shoppinglist
   *
   * @param shoppinglist the shoppinglist to save
   */
  @PUT
  @Path("/ShoppingLists/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public int addShoppingList(final ShoppingList shoppinglist) {
    int newId = shoppinglist.getId();
    System.out.println(shoppinglist);
    if (newId == -1) {
      newId = FileHandler.readMaxID();
      newId++;
      shoppinglist.setId(newId);
      FileHandler.writeMaxID(newId);
    }
    for (String x : shoppinglist.getPersonList()) {
      Person aperson = FileHandler.readPerson(x);
      if (!aperson.getShoppingLists().contains(newId) && aperson != null) {
        aperson.addShoppingList(newId);
        FileHandler.writePerson(aperson);
      }
    }
    FileHandler.writeFile(shoppinglist);
    return newId;
  }

}
