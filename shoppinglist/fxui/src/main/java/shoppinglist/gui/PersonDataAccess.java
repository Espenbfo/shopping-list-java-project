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
import shoppinglist.restapi.LoginResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class PersonDataAccess {

  private final String baseUrlString;

  public PersonDataAccess(final String baseUrlString) {
    this.baseUrlString = baseUrlString;
  }

  private final static ObjectMapper mapper = new ObjectMapper();

  /**
   * Gets the common Uri with an added part at the end
   *
   * @param path the path to add
   * @return the URI
   */
  private URI getRequestUri(final String path) {
    try {
      return new URI(baseUrlString + path);
    } catch (final URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Saves a person on the server
   *
   * @param person the person to save
   */
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

  /**
   * Gets the person with the given username.
   *
   * @param person the username
   * @return the person
   */
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

  /**
   * Checks if a user logs in with the correct password
   *
   * @param person   the username
   * @param password the password
   * @return the Person class of the username if the password is correct
   */
  public Person putLogin(final String person, final String password) {

    LoginResource loginResource = new LoginResource(new Person(person), password);
    try {
      System.out.println(mapper.writeValueAsString(loginResource));
      final HttpRequest request =
              HttpRequest.newBuilder(getRequestUri("/Login/login"))
                      .header("Content-Type", "application/json")
                      .header("Accept", "application/json")
                      .PUT(BodyPublishers.ofString(mapper.writeValueAsString(loginResource)))
                      .build();

      final HttpResponse<String> response =
              HttpClient.newBuilder().build().send(
                      request,
                      HttpResponse.BodyHandlers.ofString()
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

  /**
   * Registers a person with the given password.
   *
   * @param person   the new person
   * @param password the new password
   */
  public void putRegister(final Person person, final String password) {
    try {
      String name = person.getUserName();
      LoginResource loginResource = new LoginResource(person, password);
      final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/Login/register/" + name))
              .header("Content-Type", "application/json")
              .header("Accept", "application/json")
              .PUT(BodyPublishers.ofString(mapper.writeValueAsString(loginResource)))
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
}
