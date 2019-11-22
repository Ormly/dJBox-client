package org.pineapple.api.scene;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pineapple.api.controller.UserIPConnectController;

public class UserIPConnectScene implements SceneMaker {

    private Stage stage;

    public UserIPConnectScene(Stage stage) {this.stage = stage;}

    @Override
    public Scene getScene(){
        UserIPConnectController controller = new UserIPConnectController(stage);

        Button button = new Button("Connect");
        button.setOnAction(e -> controller.connectButtonHandle());

        Image image = new Image("PlaceHolder.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Label label = new Label("Jukebox IP address:");

        TextField textField = new TextField();
        textField.setText("127.0.0.1");

        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(label,textField);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(imageView,hBox,button);

        Scene scene = new Scene(root,800,600);
        return scene;
    }
}
