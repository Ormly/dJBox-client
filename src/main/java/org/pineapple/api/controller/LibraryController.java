package org.pineapple.api.controller;

import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.api.scene.SceneName;

public class LibraryController {

    private Stage stage;

    public LibraryController(Stage stage) { this.stage = stage; }

    //Button handlers
    public void logoutButtonHandle() {
        stage.setScene(Main.getScenes().get(SceneName.USERLOGINSCENE));
        stage.setTitle("dJBox - Login");
    }

    public void queueButtonHandle() {
        stage.setScene(Main.getScenes().get(SceneName.QUEUESCENE));
        stage.setTitle("dJBox - Queue");
    }

    public void addToQueueButtonHandle() {
        System.out.println("Song added to queue");
    }
}
