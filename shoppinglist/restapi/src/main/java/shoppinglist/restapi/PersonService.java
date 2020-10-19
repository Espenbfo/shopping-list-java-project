package shoppinglist.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(PersonService.PERSON_SERVICE_PATH)
public class PersonService {

  public static final String PERSON_SERVICE_PATH = "name"; //?

  private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

  @Inject
  private Person person;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson() {
    return person;
  }

  /**
   * Returns the shoppinglist with the provided name
   * (as a resource to support chaining path elements).
   *
   * @param title the title of the shoppinglist
   */
  @Path("/{title}")
  public PersonResource getPerson(@PathParam("title") String title) {
    ShoppingList shoppinglist  = getPerson().getShoppingList(title);
    LOG.debug("Sub-resource for Person " + title + ": " + shoppinglist);
    return new PersonResource(person, title, shoppinglist);
  }
}
