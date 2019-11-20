package org.pineapple;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.core.Song;

import java.util.List;

public class Main extends Application
{
    //TODO: do this for real

    public static void main(String[] args)
    {
        JukeBoxClient test = new JukeBoxClient();

        // Get Token
        System.out.println("Token returned from Server: " + test.getTokenTest());

        // Get List
        System.out.println("----------------------");
        System.out.println("Test to get queue:");

        String token = test.getUserData().getSecurityToken();
        List<Song> queue = test.doGetQueue(token);

        for(Song song : queue) {
            System.out.println(song.getTitle());
        }
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
