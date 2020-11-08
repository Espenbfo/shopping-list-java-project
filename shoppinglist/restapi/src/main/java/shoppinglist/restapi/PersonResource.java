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

  private Person person;
  private ShoppingList shoppingList;

  /**
   * Initializes PersonResource
   *
   * @param person the person
   * @param id the id of the shoppinglist
   * @param shoppingList the shoppingList, or null
   */
  public PersonResource(Person person, ShoppingList shoppingList) {
    this.person = person;
    this.shoppingList = shoppingList;
  }

  public void setId(int id){
    shoppingList.setId(id);
    person.addShoppingList(id);
  }

  public Person getPerson(){
    return person;
  }

  public ShoppingList getShoppingList(){
    return shoppingList;
  }





}
