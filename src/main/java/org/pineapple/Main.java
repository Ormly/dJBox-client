package org.pineapple;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.pineapple.ui.controller.Controller;
import org.pineapple.ui.scene.*;
import org.pineapple.core.JukeBoxClient;

public class Main extends Application
{
     public static void main(String[] args)
    {
        launch();
    }

    public void start(Stage stage)
    {
        JukeBoxClient test = JukeBoxClient.getJukeBoxClientInstance();
        Controller controller = new Controller(stage, test);

        // Initial scene is Connect IP
        stage.getIcons().add(new Image("ananas_color.png"));
        stage.setScene(controller.getScenes().get(SceneEnum.USERIPCONNECTSCENE));
        stage.setTitle("dJBox - IP connect");
        stage.centerOnScreen();
        stage.show();
    }
}
