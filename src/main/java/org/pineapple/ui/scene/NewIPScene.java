package org.pineapple.ui.scene;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pineapple.ui.controller.Controller;

public class NewIPScene extends SceneMaker
{
    public NewIPScene(Stage stage, Controller controller)
    {
        super(stage, controller, 300,300);

        Label nameLabel = new Label("Name");
        TextField nameTextField = new TextField();
        Label ipLabel = new Label("IP Address");
        TextField ipTextField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0,nameLabel,nameTextField);
        gridPane.addRow(1,ipLabel,ipTextField);

        Button addButton = new Button("Add");
        addButton.setDefaultButton(true);
        addButton.setOnAction(e -> {
            controller.addIPButtonHandleNewIPScene(nameTextField.getText(), ipTextField.getText());
            nameTextField.setText("");
            ipTextField.setText("");
        });

        VBox root = new VBox(10,gridPane,addButton);
        root.setAlignment(Pos.CENTER);

        this.setRoot(root);
    }
}
