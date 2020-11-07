package shoppinglist.gui;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Collections;
import shoppinglist.core.Person;


public class PersonDataAccess  {

private final String baseUrlString;

    public PersonDataAccess(final String baseUrlString) {
        this.baseUrlString = baseUrlString;
    }

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
            ObjectMapper mapper = new ObjectMapper();
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



}
