package org.pineapple.ui.controller;

import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.ui.scene.Scene;

public class LibraryController extends Controller {

    public LibraryController(Stage stage, JukeBoxClient jukeBoxClient) {
        super(stage, jukeBoxClient);
    }

    //Button handlers
    public void logoutButtonHandle() {
        stage.setScene(Main.getScenes().get(Scene.USERLOGINSCENE));
        stage.setTitle("dJBox - Login");
    }

    public void queueButtonHandle() {
        stage.setScene(Main.getScenes().get(Scene.QUEUESCENE));
        stage.setTitle("dJBox - Queue");
    }

    public void addToQueueButtonHandle() {
        System.out.println("Song added to queue");
    }
}
