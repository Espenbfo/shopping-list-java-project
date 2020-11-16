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
import shoppinglist.core.ShoppingList;

public class ShoppingListDataAccess {

  private final String baseUrlString;

  public ShoppingListDataAccess(final String baseUrlString) {
    this.baseUrlString = baseUrlString;
  }

  private URI getRequestUri(final String path) {
    try {
      return new URI(baseUrlString + path);
    } catch (final URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * PUTs a shoppinglist to server.
   *
   * @param shoppingList the shoppinglist to PUT
   * @return the id of the ShoppingList that was PUT after the PUT
   */
  public int putShoppingList(final ShoppingList shoppingList) {
    int id = shoppingList.getId();
    try {
      int index = shoppingList.getId();
      ObjectMapper mapper = new ObjectMapper();
      final HttpRequest request = HttpRequest
              .newBuilder(getRequestUri("/Persons/ShoppingLists/" + index))
              .header("Content-Type", "application/json")
              .header("Accept", "application/json")
              .PUT(BodyPublishers.ofString(mapper.writeValueAsString(shoppingList)))
              .build();
      System.out.println(mapper.writeValueAsString(shoppingList));
      final HttpResponse<InputStream> response =
              HttpClient.newBuilder().build().send(
                      request, HttpResponse.BodyHandlers.ofInputStream()
              );
      id = mapper.readValue(response.body(), int.class);
    } catch (final JsonProcessingException e) {
      throw new RuntimeException(e);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    return id;

  }

  /**
   * GETs a shoppinglist from server.
   *
   * @param id the id of the shoppinglist to GET
   * @return the shoppinglist with the id
   */
  public ShoppingList getShoppingList(final int id) {

    final HttpRequest request =
            HttpRequest.newBuilder(getRequestUri("/Persons/ShoppingLists/" + id))
                    .header("Accept", "application/json").GET().build();
    try {
      final HttpResponse<InputStream> response =
              HttpClient.newBuilder().build().send(
                      request, HttpResponse.BodyHandlers.ofInputStream()
              );
      ObjectMapper mapper = new ObjectMapper();
      ShoppingList out = mapper.readValue(response.body(), ShoppingList.class);
      return out;
    } catch (IOException | InterruptedException e) {
      System.out.println("6");
      System.err.println(e.toString());
    }
    return null;
  }

}
