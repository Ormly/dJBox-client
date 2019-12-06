package org.pineapple.ui.scene;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pineapple.core.JukeBoxClient;

public abstract class SceneMaker extends Scene {

    protected Stage stage;
    protected JukeBoxClient jukeBoxClient;

    public SceneMaker(Stage stage, JukeBoxClient jukeBoxClient, double sizeX, double sizeY){
        super(new AnchorPane(),sizeX,sizeY);
        this.stage = stage;
        this.jukeBoxClient = jukeBoxClient;
    }
}
