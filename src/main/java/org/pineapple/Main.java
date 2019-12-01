package org.pineapple;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pineapple.ui.scene.*;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.core.Song;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application
{

    // Scenes are mapped to a scene enum into a HashMap
    private static Map<SceneName, Scene> scenes = new HashMap<>();

    public static void main(String[] args)
    {
        JukeBoxClient test = JukeBoxClient.getJukeBoxClientInstance();
        test.doGetQueue();
        launch();
    }

    public void start(Stage stage)
    throws Exception
    {
        // Scenes are created and stored in the HashMap
        scenes.put(SceneName.USERIPCONNECTSCENE, new UserIPConnectScene(stage).getScene());
        scenes.put(SceneName.USERLOGINSCENE, new UserLoginScene(stage).getScene());
        scenes.put(SceneName.QUEUESCENE, new QueueScene(stage).getScene());
        scenes.put(SceneName.LIBRARYSCENE, new LibraryScene(stage).getScene());


        AnchorPane rootNode = new AnchorPane();

        // Initial scene is Connect IP
        stage.getIcons().add(new Image("ananas_color.png"));
        stage.setScene(scenes.get(SceneName.USERIPCONNECTSCENE));
        stage.setTitle("dJBox - IP connect");
        stage.centerOnScreen();
        stage.show();
    }

    // Returns scene by name
    public static Map<SceneName, Scene> getScenes() {return scenes;}
}
