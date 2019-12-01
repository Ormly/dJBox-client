package org.pineapple;

import javafx.application.Application;
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
    private static Map<Scene, javafx.scene.Scene> scenes = new HashMap<>();
    private static JukeBoxClient test = JukeBoxClient.getJukeBoxClientInstance();;

    public static void main(String[] args)
    {
        //JukeBoxClient test = JukeBoxClient.getJukeBoxClientInstance();
        test.doGetQueue();
        launch();
    }

    public void start(Stage stage)
    throws Exception
    {
        // Scenes are created and stored in the HashMap
        scenes.put(Scene.USERIPCONNECTSCENE, new UserIPConnectScene(stage, test).getScene());
        scenes.put(Scene.USERLOGINSCENE, new UserLoginScene(stage, test).getScene());
        scenes.put(Scene.QUEUESCENE, new QueueScene(stage, test).getScene());
        scenes.put(Scene.LIBRARYSCENE, new LibraryScene(stage, test).getScene());


        AnchorPane rootNode = new AnchorPane();

        // Initial scene is Connect IP
        stage.getIcons().add(new Image("ananas_color.png"));
        stage.setScene(scenes.get(Scene.USERIPCONNECTSCENE));
        stage.setTitle("dJBox - IP connect");
        stage.centerOnScreen();
        stage.show();
    }

    // Returns scene by name
    public static Map<Scene, javafx.scene.Scene> getScenes() {return scenes;}
}
