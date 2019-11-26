package org.pineapple.api.scene;

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
import org.pineapple.api.controller.LibraryController;

public class LibraryScene implements SceneMaker {
    private Stage stage;

    public LibraryScene(Stage stage){this.stage=stage;}

    @Override
    public Scene getScene() {
        // Uses controller for button handling
        LibraryController controller = new LibraryController(stage);

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
        songsTableView.setPlaceholder(new Label("No songs are in the library please add a song"));

        // Stacks search bar on top of song list
        VBox leftVBox = new VBox(searchTextField,songsTableView);

        //Menu options in top right queue and logout
        Tooltip queueTooltip = new Tooltip("Queue");
        Image queueImage = new Image("PlaceHolder.png");
        ImageView queueImageView = new ImageView(queueImage);
        queueImageView.setFitWidth(25);
        queueImageView.setFitHeight(25);
        queueImageView.setPickOnBounds(true);
        Tooltip.install(queueImageView,queueTooltip);
        queueImageView.setOnMouseClicked(e -> controller.queueButtonHandle());

        Tooltip logoutTooltip = new Tooltip("Log out");
        Image logoutImage = new Image("PlaceHolder.png");
        ImageView logoutImageView = new ImageView(logoutImage);
        logoutImageView.setFitWidth(25);
        logoutImageView.setFitHeight(25);
        logoutImageView.setPickOnBounds(true);
        Tooltip.install(logoutImageView,logoutTooltip);
        logoutImageView.setOnMouseClicked(e -> controller.logoutButtonHandle());

        // Menu options are next to each other
        HBox rightTopBorderHBox = new HBox(20);
        rightTopBorderHBox.getChildren().addAll(queueImageView,logoutImageView);
        rightTopBorderHBox.setAlignment(Pos.CENTER_RIGHT);

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
        VBox songInfoVBox = new VBox(10);
        songInfoVBox.setAlignment(Pos.CENTER);
        songInfoVBox.getChildren().addAll(albumImageView,titleLabel,artistLabel,albumLabel,addToQueueButton);

        // Top is for menu options, center is for song information
        BorderPane rightBorderPane = new BorderPane();
        rightBorderPane.setTop(rightTopBorderHBox);
        rightBorderPane.setCenter(songInfoVBox);

        // Main element search bar on top of song list on left, menu options and song information on right
        HBox root = new HBox(leftVBox,rightBorderPane);
        root.setSpacing(20);
        root.setPadding(new Insets(10));

        // left and right sides have equal width
        rightBorderPane.prefWidthProperty().bind(root.widthProperty());
        leftVBox.prefWidthProperty().bind(root.widthProperty());

        Scene scene = new Scene(root,800,600);
        return scene;
    }
}
