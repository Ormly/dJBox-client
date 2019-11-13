package org.pineapple;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args){
        launch();
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
