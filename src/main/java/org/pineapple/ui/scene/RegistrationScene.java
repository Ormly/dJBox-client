package org.pineapple.ui.scene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.pineapple.ui.controller.Controller;

/**
 * Represents a scene for registering a new user.
 */
public class RegistrationScene extends SceneMaker
{
    private TextField emailTextField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Label responseLabel;

    /**
     * Constructor for registration scene
     *
     * @param stage      window
     * @param controller controls scene commands
     */
    public RegistrationScene(Stage stage, Controller controller)
    {
        super(stage, controller, 300, 300);

        // Labels
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        Label confirmPasswordLabel = new Label("Confirm Password");
        responseLabel = new Label("");
        responseLabel.setTextFill(Color.RED);

        // Input fields
        emailTextField = new TextField();
        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();

        // Buttons
        Button signUpButton = new Button("Sign Up");
        signUpButton.setDefaultButton(true);
        signUpButton.setOnAction(e -> controller.signUpButtonRegistration(emailTextField,passwordField,confirmPasswordField,responseLabel));

        // Layout of labels and input fields
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0,emailLabel,emailTextField);
        gridPane.addRow(1,passwordLabel,passwordField);
        gridPane.addRow(2,confirmPasswordLabel,confirmPasswordField);

        // Top to bottom labels and input fields, signup button, response label
        VBox root = new VBox(10,gridPane,signUpButton,responseLabel);
        root.setAlignment(Pos.CENTER);
        this.setRoot(root);
    }

    /**
     * Clears all input fields.
     */
    public void clearFields()
    {
        emailTextField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        responseLabel.setText("");
    }
}
