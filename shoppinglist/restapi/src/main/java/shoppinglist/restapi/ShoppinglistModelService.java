package shoppinglist.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todolist.core.TodoList;
import todolist.core.TodoModel;

@Path(TodoModelService.TODO_MODEL_SERVICE_PATH)
public class TodoModelService {

  public static final String TODO_MODEL_SERVICE_PATH = "todo";

  private static final Logger LOG = LoggerFactory.getLogger(TodoModelService.class);

  @Inject
  private TodoModel todoModel;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public TodoModel getTodoModel() {
    return todoModel;
  }

  /**
   * Returns the TodoList with the provided name
   * (as a resource to support chaining path elements).
   *
   * @param name the name of the todo list
   */
  @Path("/{name}")
  public TodoListResource getTodoList(@PathParam("name") String name) {
    TodoList todoList = getTodoModel().getTodoList(name);
    LOG.debug("Sub-resource for TodoList " + name + ": " + todoList);
    return new TodoListResource(todoModel, name, todoList);
  }
}
