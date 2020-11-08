package shoppinglist.restapi;


import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.core.Person;
import shoppinglist.core.ShoppingList;
import shoppinglist.core.Passwords;
import shoppinglist.restapi.PersonResource;
import shoppinglist.storage.FileHandler;

@Path(LoginService.LOGIN_SERVICE_PATH)
public class LoginService {

  /**
   * the service path for the server
   */
  public static final String LOGIN_SERVICE_PATH = "Login";

  /**
   * logger for logging server issues
   */
  private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);


  /**
   *
   * @param userName
   * @return blablabla
   */
  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Person checkLogin(LoginResource loginResource) {
    System.out.println("Post :" + loginResource.getPerson() + " " + loginResource.getPassword());
    if (true) {
      return FileHandler.readPerson(loginResource.getPerson());
    }
    return null;
  }

  @GET
  @Path("")
  public int testLogin() {
    return 0;
  }
}
