package shoppinglist.restapi;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.core.Person;
import shoppinglist.core.ShoppingList;
import shoppinglist.restapi.PersonResource;
import shoppinglist.storage.FileHandler;

@Path(PersonService.PERSON_SERVICE_PATH)
public class PersonService {

  public static final String PERSON_SERVICE_PATH = "Gud"; //?

  private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);
  /*
  @Inject
  private Person person;
*/
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson() {
    return new Person();
  }

  /**
   * Returns the shoppinglist with the provided id
   * (as a resource to support chaining path elements).
   *
   * @param id the id of the shoppinglist
   */
  @Path("/{id}")
  public PersonResource getShoppingList(@PathParam("id") int id) {
    ShoppingList shoppinglist  = FileHandler.readFile(id);
    LOG.debug("Sub-resource for Person " + id + ": " + shoppinglist);
    Person person = new Person();
    return new PersonResource(person, id, shoppinglist);
  }
}
