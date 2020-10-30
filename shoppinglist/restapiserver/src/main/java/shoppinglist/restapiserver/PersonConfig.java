package shoppinglist.restapiserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import shoppinglist.core.Person;
import shoppinglist.restapi.PersonService;

public class PersonConfig extends ResourceConfig {

    public static Person person = new Person();

    public PersonConfig() {

        ResourceConfig rc = new ResourceConfig();

        rc.register(testPage.class);
        rc.register(PersonService.class);

        register(new AbstractBinder() {
        @Override
        protected void configure() {
            bind(person);
        }
      });

  }
}
