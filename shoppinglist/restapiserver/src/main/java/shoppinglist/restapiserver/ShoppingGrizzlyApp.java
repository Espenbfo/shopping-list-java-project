package shoppinglist.restapiserver;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


public class ShoppingGrizzlyApp{

    private static URI serverUri = URI.create("http://localhost:8080/index/");


    public static HttpServer start() throws IOException{
        int waitTime = 5;
        ResourceConfig rc = new ResourceConfig().packages("shoppinglist.restapiserver.testPage");
        System.out.println(rc);
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(serverUri,rc);
        System.out.println(httpServer);
        while (waitTime>0){
            try {
                URL clientUrl = new URL("http://localhost:8080/index/testPage");
                HttpURLConnection connection = (HttpURLConnection) clientUrl.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                System.out.println("Trying " + clientUrl + ": " + responseCode);
                connection.disconnect();
                if (responseCode == 200) {
                    return httpServer;
                }
            } catch (final RuntimeException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
                waitTime -= 1;
            } catch (final InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static void stop(HttpServer server){
        server.shutdown();
    }

    public static void main(final String[] args) throws IOException {
        try {
            final HttpServer server = start();
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
            Thread.currentThread().join();
        } catch (final InterruptedException ex) {
            Logger.getLogger(ShoppingGrizzlyApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}