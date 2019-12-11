package org.pineapple.ui.scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pineapple.core.JukeBox;
import org.pineapple.ui.controller.Controller;

public class UserIPConnectScene extends SceneMaker
{
    private TableView<JukeBox> jukeBoxTableView;
    private ObservableList<JukeBox> jukeBoxObservableList = FXCollections.observableArrayList();

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
        TableColumn<JukeBox, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(149);
        TableColumn<JukeBox, String> ipAddressColumn = new TableColumn<>("IP");
        ipAddressColumn.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        ipAddressColumn.setPrefWidth(149);
        jukeBoxTableView.getColumns().add(nameColumn);
        jukeBoxTableView.getColumns().add(ipAddressColumn);
        jukeBoxTableView.setItems(jukeBoxObservableList);
        jukeBoxTableView.setMaxSize(300,300);

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
        deleteButton.setOnAction(e -> {
            JukeBox jukeBox = jukeBoxTableView.getSelectionModel().getSelectedItem();
            controller.deleteIPButtonHandleUserIPConnectScene(jukeBox);
        });
        connectButton.setOnAction(e -> {
            JukeBox jukeBox = jukeBoxTableView.getSelectionModel().getSelectedItem();
            controller.connectButtonHandleUserIPConnectScene(jukeBox);
        });
        this.setRoot(root);
    }
    public ObservableList<JukeBox> getJukeBoxObservableList()
    {
        return jukeBoxObservableList;
    }
    public TableView<JukeBox> getJukeBoxTableView() { return jukeBoxTableView; }
}
