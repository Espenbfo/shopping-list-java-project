package shoppinglist.restapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.core.Person;
import shoppinglist.core.ShoppingList;



public class PersonResource {

  private static final Logger LOG = LoggerFactory.getLogger(PersonResource.class);

  private final Person person;
  private final int id;
  private final ShoppingList shoppingList;

  /**
   * Initializes PersonResource
   *
   * @param person the person
   * @param id the id of the shoppinglist
   * @param shoppingList the shoppingList, or null
   */
  public PersonResource(Person person, int id, ShoppingList shoppingList) {
    this.person = person;
    this.id = id;
    this.shoppingList = shoppingList;
  }

  /**
   * Gets the corresponding ShoppingList.
   *
   * @return the corresponding ShoppingList
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ShoppingList getShoppingList() {
    if (this.shoppingList == null) {
      throw new IllegalArgumentException("no shoppinglist with id  " + id + ".");
    }
    LOG.debug("getShoppingList({})", id);
    return this.shoppingList;
  }

  
  /**
   * Replaces or adds a shoppingList.
   *
   * @param shoppingList the shoppingList to add
   * @return true if it was added, false if it replaced
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public void addShoppingList(ShoppingList shoppingList) {
        LOG.debug("addShoppingList({})", shoppingList);
        this.person.addShoppingList(shoppingList);

   
  }

}