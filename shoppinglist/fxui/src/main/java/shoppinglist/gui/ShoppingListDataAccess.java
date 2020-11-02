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
import shoppinglist.core.ShoppingList;
import shoppinglist.core.Person;


public class ShoppingListDataAccess  {

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

    public void putShoppingList(final ShoppingList shoppingList) {
        try {
            int index = shoppingList.getId();
            ObjectMapper mapper = new ObjectMapper();
            final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/Shoppinglists/" + index))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .PUT(BodyPublishers.ofString(mapper.writeValueAsString(shoppingList)))
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

    public ShoppingList getShoppingList(final int id) {

        final HttpRequest request =
                HttpRequest.newBuilder(getRequestUri("/Shoppinglists/" + id))
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
            System.err.println(e.toString());
        }
        return null;
    }

    }