package org.pineapple.ui.scene;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.util.Duration;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.core.Song;
import org.pineapple.ui.controller.Controller;

public class QueueScene extends SceneMaker {

    private  Timeline timeline;

    public QueueScene(Stage stage, JukeBoxClient jukeBoxClient) {
        super(stage, jukeBoxClient,800,600);
        // Uses controller for button handling
        Controller controller = new Controller(stage, jukeBoxClient);

        // Lists songs in the queue
        TableView<Song> tableView = new TableView<>();
        TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setSortable(false);
        TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        artistColumn.setSortable(false);
        TableColumn<Song, String> albumColumn = new TableColumn<>("Album");
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        albumColumn.setSortable(false);
        tableView.getColumns().add(titleColumn);
        tableView.getColumns().add(artistColumn);
        tableView.getColumns().add(albumColumn);
        tableView.setPlaceholder(new Label("No songs are in the queue please add a song"));
        ObservableList<Song> observableSongList = FXCollections.observableArrayList();
        timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            observableSongList.clear();
            observableSongList.addAll(controller.doGetQueue());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Wrap observableList in FilteredList (Showing all data initially)
        FilteredList<Song> filteredList = new FilteredList<>(observableSongList, p -> true);

        // Search bar
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search for a song");
        searchTextField.textProperty().addListener((observable, oldValue, newValue)-> filteredList.setPredicate(song -> {
            // Empty displays all
            if (newValue == null || newValue.isEmpty())
                return true;

            // Compare song title, album and artist with search bar
            String lowerCaseFilter = newValue.toLowerCase();
            if (song.getTitle().toLowerCase().contains(lowerCaseFilter))
                return true;
            else if (song.getArtist().toLowerCase().contains(lowerCaseFilter))
                return true;
            else return song.getAlbum().toLowerCase().contains(lowerCaseFilter);
        }));

        tableView.setItems(filteredList);

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

        this.setRoot(root);
    }
}
