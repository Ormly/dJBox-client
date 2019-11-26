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
        // Controller for button handling
        UserLoginController controller = new UserLoginController(stage);

        // dJBox logo
        Image logoImage = new Image("ananas_color.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(100);
        logoImageView.setPreserveRatio(true);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> controller.loginButtonHandle());

        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        TextField usernameTextField = new TextField();
        PasswordField passwordPasswordField = new PasswordField();

        // left to right, Username:, textfield for username
        HBox usernameHBox = new HBox(20);
        usernameHBox.setAlignment(Pos.CENTER);
        usernameHBox.getChildren().addAll(usernameLabel,usernameTextField);

        // left to right Password, passwordfield for password
        HBox passwordHBox = new HBox(20);
        passwordHBox.setAlignment(Pos.CENTER);
        passwordHBox.getChildren().addAll(passwordLabel,passwordPasswordField);

        // top to bottom, dJBox logo, username and field, password and field, button
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(logoImageView,usernameHBox,passwordHBox,loginButton);

        Scene scene = new Scene(root,800,600);

        return scene;
    }
}
