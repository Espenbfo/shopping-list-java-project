package shoppinglist.restapiserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import shoppinglist.core.Person;
import shoppinglist.restapi.LoginService;
import shoppinglist.restapi.PersonService;

public class PersonConfig extends ResourceConfig {

  static final ArrayList<Person> persons = new ArrayList<>();

/**
 * New config object.
 * Registers the necessary classes for loading persons to server. 
 * 
 */
  public PersonConfig() {
    register(LoginService.class);
    register(TestPage.class);
    register(PersonService.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(persons);
      }
    });
  }
}
