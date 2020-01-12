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
import org.pineapple.core.JukeBoxIPNamePair;
import org.pineapple.ui.controller.Controller;

import java.util.HashMap;
import java.util.List;
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
        Image logoImage = new Image("Pineapple_logo.png");
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
        List<JukeBoxIPNamePair> ipNamePairs = controller.getIPNamePairs();
        for(int i = 0; i < ipNamePairs.size(); i++)
            jukeBoxHashMap.put(ipNamePairs.get(i).getJukeBoxName(),ipNamePairs.get(i).getJukeBoxIP());
        // jukeBoxHashMap.put("localhost","localhost");
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

        // Button handlers
        newButton.setOnAction(e -> controller.newIPButtonUserIPConnect());
        editButton.setOnAction(e -> controller.editIPButtonUserIPConnect());
        deleteButton.setOnAction(e -> controller.deleteIPButtonUserIPConnect());
        connectButton.setOnAction(e -> controller.connectButtonUserIPConnect());
        this.setRoot(root);
    }

    /**
     * Returns selected key from table selection
     * @return key
     */
    public String getKeyFromTableSelection() { return jukeBoxTableView.getSelectionModel().getSelectedItem().getKey(); }

    /**
     * Returns selected ip from table selection
     * @return ip
     */
    public String getIPFromTableSelection() { return jukeBoxTableView.getSelectionModel().getSelectedItem().getValue(); }

    /**
     * Updates table list by clearing and adding hashmap
     */
    public void updateObservableList()
    {
        jukeBoxObservableList.clear();
        jukeBoxObservableList.addAll(jukeBoxHashMap.entrySet());
    }

    /**
     * Puts name and ip into hashmap
     * @param name
     * @param ip
     */
    public void putHashMap(String name, String ip) { jukeBoxHashMap.put(name, ip); }

    /**
     * Removes key from hashmap
     * @param key
     */
    public void removeHashMap(String key) { jukeBoxHashMap.remove(key); }
}
