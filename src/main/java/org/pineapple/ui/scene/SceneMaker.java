package org.pineapple.ui.scene;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pineapple.core.JukeBoxClient;

public abstract class SceneMaker {

    protected Stage stage;
    protected JukeBoxClient jukeBoxClient;

    public SceneMaker(Stage stage, JukeBoxClient jukeBoxClient){
        this.stage = stage;
        this.jukeBoxClient = jukeBoxClient;
    }

    abstract Scene getScene();
}
