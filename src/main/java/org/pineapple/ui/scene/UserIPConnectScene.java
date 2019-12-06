package org.pineapple.ui.scene;

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
import org.pineapple.core.JukeBoxClient;
import org.pineapple.ui.controller.Controller;

public class UserIPConnectScene extends SceneMaker {

    public UserIPConnectScene(Stage stage, JukeBoxClient jukeBoxClient) {
        super(stage, jukeBoxClient,800,600);
        // Button handler
        Controller controller = new Controller(stage, jukeBoxClient);

        // Connect to IP button
        Button connectButton = new Button("Connect");
        connectButton.setOnAction(e -> controller.connectButtonHandle());

        // Image for dJBox logo
        Image logoImage = new Image("ananas_color.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(100);
        logoImageView.setPreserveRatio(true);

        Label ipAddressLabel = new Label("Jukebox IP address:");

        TextField ipAddressTextField = new TextField();
        ipAddressTextField.setText("127.0.0.1");

        // left to right JukeBox IP address, input field for IP address
        HBox ipAddressHBox = new HBox(20);
        ipAddressHBox.setAlignment(Pos.CENTER);
        ipAddressHBox.getChildren().addAll(ipAddressLabel,ipAddressTextField);

        // top to bottom, logo, label and input field, button
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(logoImageView,ipAddressHBox,connectButton);

        this.setRoot(root);
    }
}
