package org.pineapple.api.controller;

import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.api.scene.SceneName;

public class UserLoginController {

    private Stage stage;

    public UserLoginController(Stage stage) {this.stage = stage;}

    public void loginButtonHandle() {
        stage.setScene(Main.getScenes().get(SceneName.QUEUESCENE));
        stage.setTitle("dJBox - Queue");
    }
}
