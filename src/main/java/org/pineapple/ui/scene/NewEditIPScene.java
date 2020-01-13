package org.pineapple.ui.scene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pineapple.ui.controller.Controller;

/**
 * Represents scenes for both creating a new entry into the Jukebox IP selection scene, as well as editing an entry.
 */
public class NewEditIPScene extends SceneMaker
{
    private TextField nameTextField;
    private TextField ipTextField;
    private Button newEditButton;
    // true adding, false editing
    private boolean status;

    /**
     * Creates a new IP scene.
     * @param stage
     * @param controller
     */
    public NewEditIPScene(Stage stage, Controller controller)
    {
        super(stage, controller, 300,300);

        Label nameLabel = new Label("Name");
        nameTextField = new TextField();
        Label ipLabel = new Label("IP Address");
        ipTextField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0,nameLabel,nameTextField);
        gridPane.addRow(1,ipLabel,ipTextField);

        newEditButton = new Button("Edit");
        newEditButton.setDefaultButton(true);

        newEditButton.setOnAction(e -> {
            if(status)
            {
                controller.addIPButtonNewEditIP(nameTextField.getText(), ipTextField.getText());
            }
            else
            {
                controller.editIPButtonHandleNewEditIPScene(nameTextField.getText(), ipTextField.getText());
            }
            nameTextField.setText("");
            ipTextField.setText("");
        });

        VBox root = new VBox(10,gridPane,newEditButton);
        root.setAlignment(Pos.CENTER);

        this.setRoot(root);
    }

    /**
     * Sets the name text field.
     * @param text
     */
    public void setNameTextField(String text)
    {
        nameTextField.setText(text);
    }

    /**
     * Sets the IP text field.
     * @param text
     */
    public void setIpTextField(String text)
    {
        ipTextField.setText(text);
    }

    /**
     * Sets the status of the Button on the scene.
     * @param status
     */
    public void setStatus(boolean status) {
        this.status = status;
        if(status)
            newEditButton.setText("Add");
        else
            newEditButton.setText("Edit");
    }
}
