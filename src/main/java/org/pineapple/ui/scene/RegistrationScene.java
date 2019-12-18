package org.pineapple.ui.scene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pineapple.ui.controller.Controller;

public class RegistrationScene extends SceneMaker
{
    private TextField emailTextField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    /**
     * Parent class of scenes
     *
     * @param stage      window
     * @param controller controls scene commands
     */
    public RegistrationScene(Stage stage, Controller controller)
    {
        super(stage, controller, 300, 300);
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        Label confirmPasswordLabel = new Label("Confirm Password");
        Label responseLabel = new Label("");

        emailTextField = new TextField();
        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();

        Button signUpButton = new Button("Sign Up");
        signUpButton.setDefaultButton(true);

        signUpButton.setOnAction(e -> controller.signUpButtonRegistration(emailTextField,passwordField,confirmPasswordField,responseLabel));

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0,emailLabel,emailTextField);
        gridPane.addRow(1,passwordLabel,passwordField);
        gridPane.addRow(2,confirmPasswordLabel,confirmPasswordField);

        VBox root = new VBox(10,gridPane,signUpButton,responseLabel);
        root.setAlignment(Pos.CENTER);
        this.setRoot(root);
    }

    public void resetFields()
    {
        emailTextField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }
}
