package org.pineapple.ui.scene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

        // User and Password labels
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        // Input for user and password
        TextField usernameTextField = new TextField();
        PasswordField passwordPasswordField = new PasswordField();

        // TODO: Remove
        // Initial details for testing
        usernameTextField.setText("testperson@gmail.com");
        passwordPasswordField.setText("password");

        // Username label and input on first row
        // Password label and input on second row
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.addRow(0,usernameLabel,usernameTextField);
        gridPane.addRow(1,passwordLabel,passwordPasswordField);
        gridPane.setAlignment(Pos.CENTER);

        // Buttons
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        loginButton.setDefaultButton(true);

        HBox buttonsHBox = new HBox(10, loginButton, registerButton);
        buttonsHBox.setAlignment(Pos.CENTER);

        // Response label changes after loginButton if there is an error or is blank if successful
        Label response = new Label("");
        response.setTextFill(Color.RED);

        // Button Handle
        loginButton.setOnAction(e -> controller.loginButtonUserLogin(usernameTextField, passwordPasswordField, response));
        registerButton.setOnAction(e -> controller.registerButtonUserLogin());

        // top to bottom, dJBox logo, username and field, password and field, button, response state
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(logoImageView,gridPane,buttonsHBox, response);

        this.setRoot(root);
    }
}
