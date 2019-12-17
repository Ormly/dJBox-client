package org.pineapple.ui.scene;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pineapple.ui.controller.Controller;

import java.util.HashMap;
import java.util.Map;

public class UserIPConnectScene extends SceneMaker
{
    private Map<String, String> jukeBoxHashMap;
    private TableView<Map.Entry<String, String>> jukeBoxTableView;
    private ObservableList<Map.Entry<String, String>> jukeBoxObservableList;

    /**
     * Creates User IP Connect scene
     * @param stage window
     * @param controller controls scene commands
     */
    public UserIPConnectScene(Stage stage, Controller controller)
    {
        super(stage, controller,800,600);

        // Image for dJBox logo
        Image logoImage = new Image("ananas_color.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(100);
        logoImageView.setPreserveRatio(true);

        // Connect to IP button
        Button newButton = new Button("New");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button connectButton = new Button("Connect");
        connectButton.setDefaultButton(true);

        // Table
        jukeBoxTableView = new TableView<>();
        TableColumn<Map.Entry<String, String>,String > nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        nameColumn.setPrefWidth(149);
        TableColumn<Map.Entry<String, String>, String> ipAddressColumn = new TableColumn<>("IP");
        ipAddressColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        ipAddressColumn.setPrefWidth(149);
        jukeBoxTableView.getColumns().add(nameColumn);
        jukeBoxTableView.getColumns().add(ipAddressColumn);
        jukeBoxTableView.setMaxSize(300,300);
        jukeBoxHashMap = new HashMap<>();
        jukeBoxHashMap.put("localhost","localhost");
        jukeBoxObservableList = FXCollections.observableArrayList(jukeBoxHashMap.entrySet());
        jukeBoxTableView.setItems(jukeBoxObservableList);

        // left to right JukeBox IP address, input field for IP address
        HBox buttonsHBox = new HBox(20);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.getChildren().addAll(newButton,editButton,deleteButton,connectButton);

        // top to bottom, logo, label and input field, button
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(logoImageView, jukeBoxTableView, buttonsHBox);

        newButton.setOnAction(e -> controller.newIPButtonHandleUserIPConnectScene());
        editButton.setOnAction(e -> controller.editIPButtonHandleUserIPConnectScene());
        deleteButton.setOnAction(e -> controller.deleteIPButtonHandleUserIPConnectScene());
        connectButton.setOnAction(e -> controller.connectButtonHandleUserIPConnectScene());
        this.setRoot(root);
    }

    public Map<String, String> getJukeBoxHashMap() { return jukeBoxHashMap; }
    public ObservableList<Map.Entry<String, String>> getJukeBoxObservableList() { return jukeBoxObservableList; }
    public TableView<Map.Entry<String, String>> getJukeBoxTableView() { return jukeBoxTableView; }
}
