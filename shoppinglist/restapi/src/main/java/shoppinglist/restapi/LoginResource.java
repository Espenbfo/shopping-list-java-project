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

public class LoginResource {

    private static final Logger LOG = LoggerFactory.getLogger(LoginResource.class);

    private Person person;
    private String password;

    /**
     * Initializes PersonResource.
     *
     * @param person   the person
     * @param password the password
     */
    public LoginResource(Person person, String password) {
        this.person = person;
        this.password = password;
    }

    public LoginResource() {
        this.person = new Person();
        this.password = "";
    }

    public Person getPerson() {
        return person;
    }

    public String getPassword() {
        return password;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
