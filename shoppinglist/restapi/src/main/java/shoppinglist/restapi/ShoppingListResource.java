package todolist.restapi;

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


public class ShoppingListResource {

  private static final Logger LOG = LoggerFactory.getLogger(ShoppingListResource.class);

  private final String name;
  private final ShoppingList shoppingList;

  /**
   * Initializes this TodoListResource with appropriate context information.
   *
   * @param todoModel the TodoModel
   * @param name the todo list name
   * @param shoppingList the TodoList, or null
   */
  public ShoppingListResource(String name, ShoppingList shoppingList) {
    this.name = name;
    this.shoppingList = shoppingList;
  }

