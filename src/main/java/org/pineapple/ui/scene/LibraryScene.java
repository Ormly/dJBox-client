package org.pineapple.ui.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.core.Song;
import org.pineapple.ui.controller.Controller;

public class LibraryScene extends SceneMaker {

    public LibraryScene(Stage stage, JukeBoxClient jukeBoxClient) {
        super(stage, jukeBoxClient);
    }

    @Override
    public Scene getScene() {
        // Uses controller for button handling
        Controller controller = new Controller(stage, jukeBoxClient);

        // Search bar
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("search");

        // Lists songs in the library
        TableView<Song> songsTableView = new TableView<>();
        TableColumn<Song, String> songColumn = new TableColumn<>("Song");
        songColumn.setCellValueFactory(new PropertyValueFactory<>("song"));
        TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableColumn<Song, String> albumColumn = new TableColumn<>("Album");
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        songsTableView.getColumns().add(songColumn);
        songsTableView.getColumns().add(artistColumn);
        songsTableView.getColumns().add(albumColumn);
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

        return new Scene(root, 500, 400);
    }
}
