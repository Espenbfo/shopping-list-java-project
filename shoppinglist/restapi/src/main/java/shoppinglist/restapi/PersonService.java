package shoppinglist.restapi;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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

  public static final String PERSON_SERVICE_PATH = "Persons"; //?

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

  @GET
  @Path("/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson(@PathParam("username") String username) {
    return FileHandler.readPerson(username);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public int addPerson(final Person person) {
    return FileHandler.writePerson(person) ? 1 : 0;
  }

  @Path("/ShoppingLists/{id}")
  public PersonResource getShoppingList(@PathParam("id") int id) {
    ShoppingList shoppinglist = FileHandler.readFile(id);
    LOG.debug("Sub-resource for Person " + id + ": " + shoppinglist);
    Person person = new Person();
    return new PersonResource(person, id, shoppinglist);
  }

}
