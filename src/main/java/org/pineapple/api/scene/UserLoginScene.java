package org.pineapple.api.scene;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pineapple.api.controller.UserLoginController;

public class UserLoginScene implements SceneMaker {
    private Stage stage;

    public UserLoginScene(Stage stage) {this.stage = stage;}

    @Override
    public Scene getScene(){
        UserLoginController controller = new UserLoginController(stage);

        Image image = new Image("PlaceHolder.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Button button = new Button("Login");
        button.setOnAction(e -> controller.loginButtonHandle());

        Label label1 = new Label("Username:");
        Label label2 = new Label("Password:");

        TextField textField = new TextField();
        PasswordField passwordField = new PasswordField();

        HBox hBox1 = new HBox(20);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(label1,textField);

        HBox hBox2 = new HBox(20);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(label2,passwordField);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(imageView,hBox1,hBox2,button);

        Scene scene = new Scene(root,800,600);

        return scene;
    }
}
