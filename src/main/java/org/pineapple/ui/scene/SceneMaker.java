package org.pineapple.ui.scene;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pineapple.ui.controller.Controller;

public abstract class SceneMaker extends Scene {

    protected Stage stage;
    protected Controller controller;

    /**
     * Parent class of scenes
     * @param stage window
     * @param controller controls scene commands
     * @param sizeX window width
     * @param sizeY window height
     */
    public SceneMaker(Stage stage, Controller controller, double sizeX, double sizeY){
        super(new AnchorPane(),sizeX,sizeY);
        this.stage = stage;
        this.controller = controller;
    }
}
