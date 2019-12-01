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
import org.pineapple.ui.controller.QueueController;

public class QueueScene implements SceneMaker {
    private Stage stage;

    public QueueScene(Stage stage){this.stage=stage;}

    @Override
    public Scene getScene(){
        // Uses controller for button handling
        QueueController controller = new QueueController(stage);

        // Search bar
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("search");

        // Lists songs in the queue
        TableView tableView = new TableView();
        TableColumn<String, String> songColumn = new TableColumn<>("Song");
        songColumn.setCellValueFactory(new PropertyValueFactory<>("song"));
        TableColumn<String, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableColumn<String, String> albumColumn = new TableColumn<>("Album");
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        tableView.getColumns().addAll(songColumn,artistColumn,albumColumn);
        tableView.setPlaceholder(new Label("No songs are in the queue please add a song"));

        // Search bar on top of song list
        VBox leftVBox = new VBox(searchTextField,tableView);

        // Menu options in top right Library and logout
        Tooltip libraryTooltip = new Tooltip("Library");
        Image libraryImage = new Image("PlaceHolder.png");
        ImageView libraryImageView = new ImageView(libraryImage);
        libraryImageView.setFitWidth(25);
        libraryImageView.setFitHeight(25);
        libraryImageView.setPickOnBounds(true);
        Tooltip.install(libraryImageView,libraryTooltip);
        libraryImageView.setOnMouseClicked(e -> controller.libraryButtonHandle());

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
        rightTopBorderHBox.getChildren().addAll(libraryImageView,logoutImageView);
        rightTopBorderHBox.setAlignment(Pos.CENTER_RIGHT);

        //Album art of currently playing song
        Image currentAlbumImage = new Image("PlaceHolder.png");
        ImageView currentAlbumImageView = new ImageView((currentAlbumImage));
        currentAlbumImageView.setFitHeight(100);
        currentAlbumImageView.setFitWidth(100);

        // Song information of currently playing song
        Label currentTitleLabel = new Label("Thriller");
        Label currentArtistLabel = new Label("Michael Jackson");
        Label currentAlbumLabel = new Label("Thriller");

        // Album art, on top of song information
        VBox currentSongVBox = new VBox(10);
        currentSongVBox.setAlignment(Pos.CENTER);
        currentSongVBox.getChildren().addAll(currentAlbumImageView,currentTitleLabel,currentArtistLabel,currentAlbumLabel);

        // Right arrow
        Image rightArrowImage = new Image("PlaceHolder.png");
        ImageView rightArrowImageView = new ImageView(rightArrowImage);
        rightArrowImageView.setFitWidth(25);
        rightArrowImageView.setFitHeight(25);

        // Album art for next song
        Image nextAlbumImage = new Image("PlaceHolder.png");
        ImageView nextAlbumImageView = new ImageView(nextAlbumImage);
        nextAlbumImageView.setFitWidth(50);
        nextAlbumImageView.setFitHeight(50);

        // Song information for next song
        Label nextTitleLabel = new Label("Heart of Glass");
        Label nextArtistLabel = new Label("Blondie");
        Label nextAlbumLabel = new Label("Parallel Lines");

        // Stacks album art on top of song information for next song
        VBox nextSongVBox = new VBox(10);
        nextSongVBox.setAlignment(Pos.CENTER);
        nextSongVBox.getChildren().addAll(nextAlbumImageView,nextTitleLabel,nextArtistLabel,nextAlbumLabel);

        // from left to right currently playing song, right arrow, next song
        HBox songQueueHBox = new HBox(50);
        songQueueHBox.setAlignment(Pos.CENTER);
        songQueueHBox.getChildren().addAll(currentSongVBox,rightArrowImageView,nextSongVBox);

        // Top is for menu options, center is for song information
        BorderPane rightBorderPane = new BorderPane();
        rightBorderPane.setTop(rightTopBorderHBox);
        rightBorderPane.setCenter(songQueueHBox);

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
