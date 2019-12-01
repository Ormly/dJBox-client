package org.pineapple.ui.controller;

import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.ui.scene.SceneName;

public class UserIPConnectController {

    private Stage stage;

    public UserIPConnectController(Stage stage) {this.stage = stage;}

    // Button handlers
    public void connectButtonHandle() {
        stage.setScene(Main.getScenes().get(SceneName.USERLOGINSCENE));
        stage.setTitle("dJBox - Login");
    }

}
