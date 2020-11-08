package shoppinglist.gui;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Collections;
import shoppinglist.core.Person;
import shoppinglist.restapi.LoginResource;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class PersonDataAccess  {

private final String baseUrlString;

    public PersonDataAccess(final String baseUrlString) {
        this.baseUrlString = baseUrlString;
    }

    private final static ObjectMapper mapper = new ObjectMapper();

    private URI getRequestUri(final String path) {
        try {
            return new URI(baseUrlString + path);
        } catch (final URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void putPerson(final Person person) {
        try {
            String name = person.getUserName();
            final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/Persons/" + name))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .PUT(BodyPublishers.ofString(mapper.writeValueAsString(person)))
                    .build();
            final HttpResponse<InputStream> response =
                    HttpClient.newBuilder().build().send(
                            request, HttpResponse.BodyHandlers.ofInputStream()
                    );

        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Person getPerson(final String person) {

        final HttpRequest request =
                HttpRequest.newBuilder(getRequestUri("/Persons/" + person))
                        .header("Accept", "application/json").GET().build();
        try {
            final HttpResponse<InputStream> response =
                    HttpClient.newBuilder().build().send(
                            request, HttpResponse.BodyHandlers.ofInputStream()
                    );
            ObjectMapper mapper = new ObjectMapper();
            Person out = mapper.readValue(response.body(), Person.class);
            return out;
        } catch (IOException | InterruptedException e) {
            System.out.println("3");
            System.err.println(e.toString());
        }
        return null;
    }

    public Person postLogin(final String username, final String password) {

        LoginResource loginResource = new LoginResource(username,password);
        try {
            System.out.println(mapper.writeValueAsString(loginResource));
            final HttpRequest request =
                    HttpRequest.newBuilder(getRequestUri("/Login/login"))
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .POST(BodyPublishers.ofString(mapper.writeValueAsString(loginResource)))
                            .build();

            final HttpResponse<InputStream> response =
                    HttpClient.newBuilder().build().send(
                            request, HttpResponse.BodyHandlers.ofInputStream()
                    );
            System.out.println("here");
            System.out.println(response.body());
            Person p = mapper.readValue(response.body(), Person.class);
            return p;
        } catch (JsonProcessingException e) {
            System.err.println(e.toString());
        } catch (IOException | InterruptedException e) {
            System.err.println(e.toString());
        }
        return null;
    }
}
