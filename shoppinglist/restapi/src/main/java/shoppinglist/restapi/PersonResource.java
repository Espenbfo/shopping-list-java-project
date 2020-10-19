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


public class PersonResource {

  private static final Logger LOG = LoggerFactory.getLogger(PersonResource.class);

  private final Person person;
  private final String title;
  private final ShoppingList shoppingList;

  /**
   * Initializes PersonResource
   *
   * @param person the person
   * @param title the title of the shoppinglist
   * @param shoppingList the shoppingList, or null
   */
  public PersonResource(Person person, String title, ShoppingList shoppingList) {
    this.person = person;
    this.name = title;
    this.shoppingList = shoppingList;
  }

  /**
   * Gets the corresponding ShoppingList.
   *
   * @return the corresponding ShoppingList
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public TodoList getShoppingList() {
    if (this.shoppingList == null) {
      this.shoppingList = new ShoppingList(this.title);
    }
    LOG.debug("getShoppingList({})", title);
    return this.shoppingList;
  }

  
  /**
   * Replaces or adds a shoppingList.
   * Checks if given shoppinglist exist, and sets it to the person if it does. If not, creates new shoppinglist with given title
   *
   * @param shoppingList the shoppingList to add
   * @return true if it was added, false if it replaced
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public boolean addShoppingList(ShoppingList shoppingList) {

    ShoppingList tmpShoppingList;

    //nei?
    int maxID = Filehandler.readMaxID();
    for (int i = 0; i <= maxID; i++){
        tmpShoppingList = Filehandler.readFile(i);
        if (this.title == tmpShoppingList.getTitle())
            this.person.addShoppingList(tmpShoppingList);
            return true;
    }
    tmpShoppingList = new ShoppingList(this.title);
    this.person.addShoppingList(tmpShoppingList);
    LOG.debug("addShoppingList({})", shoppingList);
    return false;
   
  }

  
}
