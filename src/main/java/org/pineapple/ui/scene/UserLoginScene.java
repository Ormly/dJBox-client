package org.pineapple.ui.scene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.pineapple.ui.controller.Controller;

public class UserLoginScene extends SceneMaker {

    /**
     * Creates User Login scene
     * @param stage window
     * @param controller controls scene commands
     */
    public UserLoginScene(Stage stage, Controller controller) {
        super(stage, controller,800,600);

        // dJBox logo
        Image logoImage = new Image("ananas_color.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(100);
        logoImageView.setPreserveRatio(true);

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

        Button loginButton = new Button("Login");

        // Response label changes after loginButton if there is an error or is blank if successful
        Label response = new Label("");
        response.setTextFill(Color.RED);

        loginButton.setOnAction(e -> controller.loginButtonHandle(usernameTextField, passwordPasswordField, response));

        // top to bottom, dJBox logo, username and field, password and field, button, response state
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(logoImageView,usernameHBox,passwordHBox,loginButton, response);

        this.setRoot(root);
    }
}
