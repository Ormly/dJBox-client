package org.pineapple;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pineapple.core.JukeBoxClient;

public class Main extends Application
{
    //TODO: do this for real

    public static void main(String[] args)
    {
        JukeBoxClient authTest = new JukeBoxClient();
        System.out.println("Token returned from Server: " + authTest.getTokenTest());
        //launch();
    }

    public void start(Stage stage)
    throws Exception
    {
        AnchorPane rootNode = new AnchorPane();

        stage.setScene(new Scene(rootNode, 800, 600));
        stage.centerOnScreen();
        stage.show();
    }
}
