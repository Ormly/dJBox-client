package org.pineapple.ui.controller;

import javafx.stage.Stage;
import org.pineapple.core.JukeBoxClient;

public abstract class Controller {

    protected Stage stage;
    protected JukeBoxClient jukeBoxClient;

    Controller(Stage stage, JukeBoxClient jukeBoxClient) {
        this.stage = stage;
        this.jukeBoxClient = jukeBoxClient;
    }
}
