package org.pineapple.ui.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.ui.controller.LibraryController;

public class LibraryScene extends SceneMaker {

    public LibraryScene(Stage stage, JukeBoxClient jukeBoxClient) {
        super(stage, jukeBoxClient);
    }

    @Override
    public Scene getScene() {
        // Uses controller for button handling
        LibraryController controller = new LibraryController(stage, jukeBoxClient);

        // Search bar
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("search");

        // Lists songs in the library
        TableView songsTableView = new TableView();
        TableColumn<String, String> songColumn = new TableColumn<>("Song");
        songColumn.setCellValueFactory(new PropertyValueFactory<>("song"));
        TableColumn<String, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableColumn<String, String> albumColumn = new TableColumn<>("Album");
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        songsTableView.getColumns().addAll(songColumn,artistColumn,albumColumn);
        songsTableView.setPlaceholder(new Label("No songs have been added to the library"));

        // Stacks search bar on top of song list
        VBox leftVBox = new VBox(searchTextField,songsTableView);

        // Album art for currently selected song
        Image albumImage = new Image("PlaceHolder.png");
        ImageView albumImageView = new ImageView(albumImage);
        albumImageView.setFitWidth(50);
        albumImageView.setFitHeight(50);

        // Song information
        Label titleLabel = new Label("Heart of Glass");
        Label artistLabel = new Label("Blondie");
        Label albumLabel = new Label("Parallel Lines");

        // Add to queue button
        Button addToQueueButton = new Button("Add to Queue");
        addToQueueButton.setOnAction(e -> controller.addToQueueButtonHandle());

        // Album art song information and queue button
        VBox rightVBox = new VBox(10);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.getChildren().addAll(albumImageView,titleLabel,artistLabel,albumLabel,addToQueueButton);

        // Main element search bar on top of song list on left, menu options and song information on right
        HBox root = new HBox(leftVBox,rightVBox);
        root.setSpacing(20);
        root.setPadding(new Insets(10));

        // left and right sides have equal width
        rightVBox.prefWidthProperty().bind(root.widthProperty());
        leftVBox.prefWidthProperty().bind(root.widthProperty());

        Scene scene = new Scene(root,500,400);
        return scene;
    }
}
