package org.pineapple.ui.controller;

import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.ui.scene.Scene;

public class UserIPConnectController extends Controller {

    public UserIPConnectController(Stage stage, JukeBoxClient jukeBoxClient) {
        super(stage, jukeBoxClient);
    }

    // Button handlers
    public void connectButtonHandle() {
        stage.setScene(Main.getScenes().get(Scene.USERLOGINSCENE));
        stage.setTitle("dJBox - Login");
    }

}
