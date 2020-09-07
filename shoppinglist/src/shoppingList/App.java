package shoppinglist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(final Stage primaryStage) throws Exception {
    final Parent parent = FXMLLoader.load(getClass().getResource("App.fxml"));
    primaryStage.setScene(new Scene(parent));
    primaryStage.show();
  }

  public static void main(final String[] args) {
    launch(args);
  }
}
