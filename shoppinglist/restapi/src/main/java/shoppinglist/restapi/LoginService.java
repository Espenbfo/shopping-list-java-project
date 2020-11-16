package shoppinglist.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.core.Passwords;
import shoppinglist.core.Person;
import shoppinglist.core.ShoppingList;
import shoppinglist.restapi.PersonResource;
import shoppinglist.storage.FileHandler;

@Path(LoginService.LOGIN_SERVICE_PATH)
public class LoginService {

  /**
   * the service path for the server.
   */
  public static final String LOGIN_SERVICE_PATH = "Login";
  public static Passwords passwords;

  private static final  ObjectMapper mapper = new ObjectMapper();
  /**
   * logger for logging server issues.
   */
  private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);


  /**
   * Recieved Put for Person.
   *
   * @param loginResource the username, password combo
   * @return whether the person was saved or not
   */
  @PUT
  @Path("/register/{username}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public int addPerson(final LoginResource loginResource) {
    Person person = loginResource.getPerson();
    LOG.debug("addShoppingList({})", person);
    Passwords passwords = FileHandler.readPasswords();
    String password = loginResource.getPassword();
    if (passwords == null) {
      passwords = new Passwords();
    }
    passwords.setPassword(person, password);
    FileHandler.writePasswords(passwords);
    return FileHandler.writePerson(person) ? 1 : 0;
  }

  /**
   * Confirms a users password.
   *
   * @param loginResource the username, password combo
   * @return the correct person if the login succeeds
   * 
   */
  @PUT
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Person checkLogin(LoginResource loginResource) {

    passwords = FileHandler.readPasswords();
    if (passwords == null) {
      passwords = new Passwords();
    }
    Person p = FileHandler.readPerson(loginResource.getPerson().getUserName());
    if (passwords.checkPassword(
            p,
            loginResource.getPassword())) {
      return p;
    }
    return null;
  }

}
