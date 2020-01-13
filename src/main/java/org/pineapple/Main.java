package org.pineapple;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.pineapple.core.ResponseState;
import org.pineapple.ui.controller.Controller;
import org.pineapple.core.JukeBoxClient;

public class Main extends Application
{
    /**
     * Launches the main scene.
     * @param args
     */
     public static void main(String[] args)
    {
        launch();
    }

    /**
     * Sets up the start up stage of the application.
     * @param stage
     */
    public void start(Stage stage)
    {
        JukeBoxClient jukeBoxClient = JukeBoxClient.getJukeBoxClientInstance();
        Controller controller = new Controller(stage, jukeBoxClient);

        // Initial scene is Connect IP
        stage.getIcons().add(new Image("Pineapple_logo.png"));
        stage.setScene(controller.getInitialScene());
        stage.setTitle("dJBox - IP connect");
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Stops the stage. If user is logged in, logs out the user.
     */
    public void stop()
    {
        JukeBoxClient jukeBoxClient = JukeBoxClient.getJukeBoxClientInstance();
        jukeBoxClient.storePersistenceToFile();
        if(jukeBoxClient.userLoggedIn())
        {
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
}
