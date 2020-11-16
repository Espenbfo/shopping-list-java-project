package shoppinglist.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shoppinglist.core.Client;
import shoppinglist.core.Passwords;
import shoppinglist.storage.FileHandler;

public class App extends Application {

    Scene scene;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        System.out.println(getClass().getResource("/resources/shoppinglist/gui/App.fxml"));
        final Parent parent = FXMLLoader
                .load(getClass()
                        .getResource("/resources/shoppinglist/gui/LoginScreen.fxml"));
        ;
        scene = new Scene(parent);
        scene.getStylesheets()
                .add(getClass()
                        .getResource("/resources/shoppinglist/gui/style.css")
                        .toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        Client.setPasswords(FileHandler.readPasswords());
        if (Client.getPasswords() == null) {
            Client.setPasswords(new Passwords());
        }
    }

    public static void main(final String[] args) {
        launch(args);
    }

}
