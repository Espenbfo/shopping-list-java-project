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

  public static final String PERSON_SERVICE_PATH = "Persons";

  //private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);
  
  @Inject
  private ArrayList<Person> persons;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson() {
    return new Person();
  }

  @GET
  @Path("/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public Person getPerson(@PathParam("username") String username) {
      for (Person p : persons) {
          if (p.getUserName() == username){
              return p;
          }
      }
    return null;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public int addPerson(final Person person) {
    //LOG.debug("addShoppingList({})", person);
    return persons.add(person) ? 1 : 0;
  }

  @Path("/ShoppingLists/{id}")
  public PersonResource getShoppingList(@PathParam("id") int id) {
    ShoppingList shoppinglist = FileHandler.readFile(id);
    //LOG.debug("Sub-resource for Person " + id + ": " + shoppinglist);
    Person person = new Person();
    return new PersonResource(person, id, shoppinglist);
  }
  

}
