package org.pineapple;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.pineapple.core.ResponseState;
import org.pineapple.ui.controller.Controller;
import org.pineapple.core.JukeBoxClient;

public class Main extends Application
{
     public static void main(String[] args)
    {
        launch();
    }

    public void start(Stage stage)
    {
        JukeBoxClient jukeBoxClient = JukeBoxClient.getJukeBoxClientInstance();
        Controller controller = new Controller(stage, jukeBoxClient);

        // Initial scene is Connect IP
        stage.getIcons().add(new Image("ananas_color.png"));
        stage.setScene(controller.getInitialScene());
        stage.setTitle("dJBox - IP connect");
        stage.centerOnScreen();
        stage.show();
    }

    public void stop()
    {
        JukeBoxClient jukeBoxClient = JukeBoxClient.getJukeBoxClientInstance();
        ResponseState responseState = jukeBoxClient.doLogout();
        switch(responseState)
        {
            case SUCCESS:
                break;
            case AUTHFAIL:
                break;
            case CANTREACH:
                break;
        }
    }
}
