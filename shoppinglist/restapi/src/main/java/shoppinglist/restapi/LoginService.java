package shoppinglist.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

  private static final  ObjectMapper mapper = new ObjectMapper();

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
    //Gets the person from the loginresource
    Person person = loginResource.getPerson();

    //Gets the password from the loginresource
    String password = loginResource.getPassword();

    //Reads in the passwords
    Passwords passwords = FileHandler.readPasswords();

    //Creates a new password file if there are no old passwords on file.
    if (passwords == null) {
      passwords = new Passwords();
    }

    //Sets the password
    passwords.setPassword(person, password);

    //Writes the password to file
    FileHandler.writePasswords(passwords);

    //Writes the new person;
    return FileHandler.writePerson(person) ? 1 : 0;
  }

  /**
   * Confirms a users password.
   *
   * @param loginResource the username, password combo
   * @return the correct person if the login succeeds
   * 
   */
  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Person checkLogin(LoginResource loginResource) {
    //Reads in the passwords
    Passwords passwords = FileHandler.readPasswords();

    //Creates a new password file if there are no old passwords on file.
    if (passwords == null) {
      passwords = new Passwords();
    }

    //Reads the person from file as specified by loginResource
    Person p = FileHandler.readPerson(loginResource.getPerson().getUserName());

    //Checks the password and return the correct person if the password is valid
    if (passwords.checkPassword(
            p,
            loginResource.getPassword())) {
      return p;
    }

    //Returns null if the login was invalid
    return null;
  }

}
