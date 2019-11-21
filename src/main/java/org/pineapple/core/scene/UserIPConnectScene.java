package org.pineapple.core.scene;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pineapple.core.controller.UserIPConnectController;

public class SceneUserIPConnect implements SceneMaker {

    private Stage stage;

    public SceneUserIPConnect(Stage stage) {this.stage = stage;}

    @Override
    public Scene getScene(){
        UserIPConnectController controller = new UserIPConnectController(stage);

        Button button = new Button("Connect");

        Label label = new Label("Jukebox IP address:");

        TextField textField = new TextField();
        textField.setText("127.0.0.1");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(label,textField);

        VBox root = new VBox();
        root.getChildren().addAll(hBox,button);

        Scene scene = new Scene(root,800,600);

        return scene;
    }
}
