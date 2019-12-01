package org.pineapple.ui.controller;

import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.ui.scene.SceneName;

public class UserLoginController {

    private Stage stage;

    public UserLoginController(Stage stage) {this.stage = stage;}

    //Button handlers
    public void loginButtonHandle() {
        stage.setScene(Main.getScenes().get(SceneName.QUEUESCENE));
        stage.setTitle("dJBox - Queue");
    }
}
