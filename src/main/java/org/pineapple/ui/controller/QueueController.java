package org.pineapple.ui.controller;

import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.ui.scene.Scene;

public class QueueController extends Controller {

    public QueueController(Stage stage, JukeBoxClient jukeBoxClient) {
        super(stage, jukeBoxClient);
    }

    //Button handlers
    public void logoutButtonHandle() {
        stage.setScene(Main.getScenes().get(Scene.USERLOGINSCENE));
        stage.setTitle("dJBox - Login");
    }

    public void libraryButtonHandle() {
        stage.setScene(Main.getScenes().get(Scene.LIBRARYSCENE));
        stage.setTitle("dJBox - Library");
    }
}
