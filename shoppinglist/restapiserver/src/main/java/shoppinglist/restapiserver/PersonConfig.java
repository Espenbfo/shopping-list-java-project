package shoppinglist.restapiserver;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.ArrayList;

import shoppinglist.core.Person;
import shoppinglist.restapi.PersonService;

public class PersonConfig extends ResourceConfig {

    public static ArrayList<Person> persons = new ArrayList<>();

    public PersonConfig() {

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
