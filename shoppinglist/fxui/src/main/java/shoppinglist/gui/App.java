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
    //Gets the parent with our fxml file
    final Parent parent = FXMLLoader
        .load(getClass()
        .getResource("LoginScreen.fxml"));

    //Creates a new scene with the parent
    scene = new Scene(parent);

    //Loads the stylecheet
    scene.getStylesheets()
        .add(getClass()
        .getResource("style.css")
        .toExternalForm());

    //Sets the scene
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(final String[] args) {
    launch(args);
  }

}
