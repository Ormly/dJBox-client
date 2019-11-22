package org.pineapple.api.controller;

import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.api.scene.SceneName;

public class UserIPConnectController {

    private Stage stage;

    public UserIPConnectController(Stage stage) {this.stage = stage;}

    public void connectButtonHandle() {
        stage.setScene(Main.getScenes().get(SceneName.USERLOGINSCENE));
        stage.setTitle("dJBox - Login");
    }

}
