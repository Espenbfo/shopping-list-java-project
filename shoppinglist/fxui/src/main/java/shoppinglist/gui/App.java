package shoppinglist.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  Scene scene;
  @Override
  public void start(final Stage primaryStage) throws Exception {
    System.out.println(getClass().getResource("/resources/shoppinglist/gui/App.fxml"));
    final Parent parent = FXMLLoader.load(getClass().getResource("/resources/shoppinglist/gui/LoginScreen.fxml"));;
    scene  = new Scene(parent);
    scene.getStylesheets().add(getClass().getResource("/resources/shoppinglist/gui/style.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(final String[] args) {
    launch(args);
  }

}
