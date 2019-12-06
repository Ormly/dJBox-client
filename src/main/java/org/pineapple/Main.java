package org.pineapple;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pineapple.ui.scene.*;
import org.pineapple.core.JukeBoxClient;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application
{

    // Scenes are mapped to a scene enum into a HashMap
    private static Map<SceneEnum, javafx.scene.Scene> scenes = new HashMap<>();
    private static JukeBoxClient test = JukeBoxClient.getJukeBoxClientInstance();

    public static void main(String[] args)
    {
        //JukeBoxClient test = JukeBoxClient.getJukeBoxClientInstance();
        test.doGetQueue();
        launch();
    }

    public void start(Stage stage)
    {
        // Scenes are created and stored in the HashMap
        scenes.put(SceneEnum.USERIPCONNECTSCENE, new UserIPConnectScene(stage, test));
        scenes.put(SceneEnum.USERLOGINSCENE, new UserLoginScene(stage, test));
        scenes.put(SceneEnum.QUEUESCENE, new QueueScene(stage, test));
        scenes.put(SceneEnum.LIBRARYSCENE, new LibraryScene(stage, test));

        AnchorPane rootNode = new AnchorPane();

        // Initial scene is Connect IP
        stage.getIcons().add(new Image("ananas_color.png"));
        stage.setScene(scenes.get(SceneEnum.USERIPCONNECTSCENE));
        stage.setTitle("dJBox - IP connect");
        stage.centerOnScreen();
        stage.show();
    }

    // Returns scene by name
    public static Map<SceneEnum, javafx.scene.Scene> getScenes() {return scenes;}
}
