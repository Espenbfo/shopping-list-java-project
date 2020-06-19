package javafxapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AppController {
    
    @FXML
    Button clickMeButton;

    @FXML
    void handleClickMeButtonAction() {
        clickMeButton.setText("Thanks!");
    }
}
