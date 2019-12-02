package org.pineapple.ui.controller;

import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.ui.scene.Scene;

public class UserLoginController extends Controller {

    public UserLoginController(Stage stage, JukeBoxClient jukeBoxClient) {
        super(stage, jukeBoxClient);
    }

    //Button handlers
    public void loginButtonHandle(String email, String password) {
        //TODO: doAuthentication returns string, what to do with string?
        jukeBoxClient.doAuthentication(email, password);
        stage.setScene(Main.getScenes().get(Scene.QUEUESCENE));
        stage.setTitle("dJBox - Queue");
    }
}
