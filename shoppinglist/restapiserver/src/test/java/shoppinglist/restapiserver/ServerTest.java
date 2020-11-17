package shoppinglist.restapiserver;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.Thread;
import java.net.BindException;
import java.net.URI;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Collections;
import javax.ws.rs.client.Entity;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import shoppinglist.restapiserver.ShoppingGrizzlyApp;
import shoppinglist.restapi.PersonService;
import shoppinglist.restapi.LoginService;
import shoppinglist.core.*;


public class ServerTest extends JerseyTest {


  @Override
  protected ResourceConfig configure() {
    final ResourceConfig config = new PersonConfig();
    return config;
  }

  @Override
  protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
    return new GrizzlyTestContainerFactory();
  }


  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
  }

  @AfterEach
  public void tearDown() throws Exception {
    super.tearDown();
  }


  @Test
  public void testServerStart() {
    HttpServer h;
    try {
      h = ShoppingGrizzlyApp.start();
      URL clientUrl = new URL("http://localhost:8087/" + PersonService.PERSON_SERVICE_PATH);
      HttpURLConnection connection = (HttpURLConnection) clientUrl.openConnection();
      int response = connection.getResponseCode();
      assertEquals(response, 200);
      ShoppingGrizzlyApp.stop(h);
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
    }
  }


  @Test
  void testMain() {
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          ShoppingGrizzlyApp.main(new String[0]);
        }
        catch(IOException e) {
          e.printStackTrace();
          assertTrue(false);
        }
      }
    });
    t1.start();
    try {
      Thread.sleep(1000);
    }
    catch (Exception e) {

    }
    t1.interrupt();

  }


  @Test
  void testPutGet() {
    ObjectMapper om = new ObjectMapper();
    Person testindivid = new Person("Testindivid");
    try {
      final Response putResponse = target(PersonService.PERSON_SERVICE_PATH).path("Testindivid")
              .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
              .put(Entity.entity(om.writeValueAsString(testindivid), MediaType.APPLICATION_JSON));
      assertEquals(200, putResponse.getStatus());
      final Response getResponse = target(PersonService.PERSON_SERVICE_PATH).path("Testindivid")
              .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
              .get();
      assertEquals(200, getResponse.getStatus());
      assertEquals(testindivid, om.readValue(getResponse.readEntity(String.class), Person.class));

    }catch (com.fasterxml.jackson.core.JsonProcessingException e){
      e.printStackTrace();
      assertTrue(false);
    }
  }

}





