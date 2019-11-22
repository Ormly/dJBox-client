package org.pineapple.api.controller;

import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.api.scene.SceneName;

public class QueueController {

    private Stage stage;

    public QueueController(Stage stage){ this.stage = stage;}

    //Button handlers
    public void logoutButtonHandle() {
        stage.setScene(Main.getScenes().get(SceneName.USERLOGINSCENE));
        stage.setTitle("dJBox - Login");
    }

    public void libraryButtonHandle() {
        stage.setScene(Main.getScenes().get(SceneName.LIBRARYSCENE));
        stage.setTitle("dJBox - Library");
    }
}
