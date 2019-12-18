package org.pineapple.ui.scene;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.pineapple.core.Song;
import org.pineapple.ui.controller.Controller;

import java.util.List;

public class QueueScene extends SceneMaker {

    private  Timeline timeline;
    private ObservableList<Song> songObservableList = FXCollections.observableArrayList();
    private Label currentTitleLabel;
    private Label currentArtistLabel;
    private Label currentAlbumLabel;
    private Label nextTitleLabel;
    private Label nextArtistLabel;
    private Label nextAlbumLabel;

    /**
     * Creates Queue scene
     * @param stage window
     * @param controller controls scene commands
     */
    public QueueScene(Stage stage, Controller controller)
    {
        super(stage, controller,800,600);
        // Uses controller for button handling

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
        timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            updateSongObservableList(controller.getQueueList());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Wrap observableList in FilteredList (Showing all data initially)
        FilteredList<Song> filteredList = new FilteredList<>(songObservableList, p -> true);

        // Search bar
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search for a song");
        searchTextField.textProperty().addListener((observable, oldValue, newValue)
                                                           -> filteredList.setPredicate(song -> {
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
        VBox leftVBox = new VBox(searchTextField, tableView);

        // Menu options in top right Library and logout
        Tooltip libraryTooltip = new Tooltip("Library");
        Image libraryImage = new Image("PlaceHolder.png");
        ImageView libraryImageView = new ImageView(libraryImage);
        libraryImageView.setFitWidth(25);
        libraryImageView.setFitHeight(25);
        libraryImageView.setPickOnBounds(true);
        Tooltip.install(libraryImageView,libraryTooltip);
        libraryImageView.setOnMouseClicked(e -> controller.libraryButtonQueue());

        // Logout button
        Tooltip logoutTooltip = new Tooltip("Log out");
        Image logoutImage = new Image("PlaceHolder.png");
        ImageView logoutImageView = new ImageView(logoutImage);
        logoutImageView.setFitWidth(25);
        logoutImageView.setFitHeight(25);
        logoutImageView.setPickOnBounds(true);
        Tooltip.install(logoutImageView,logoutTooltip);
        logoutImageView.setOnMouseClicked(e -> controller.logoutButtonQueue());

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
        currentTitleLabel = new Label();
        currentArtistLabel = new Label();
        currentAlbumLabel = new Label();

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
        nextTitleLabel = new Label();
        nextArtistLabel = new Label();
        nextAlbumLabel = new Label();

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

    // Controller for refreshing queue
    public void playTimeLine() { timeline.play(); }
    public void stopTimeLine() { timeline.stop(); }

    /**
     * Updates the list and updates current and next song
     * @param songList
     */
    public void updateSongObservableList(List<Song> songList)
    {
        songObservableList.clear();
        songObservableList.addAll(songList);
        Song current = null;
        Song next = null;
        switch(songList.size())
        {
            case 0:
                break;
            case 1:
                current = songList.get(0);
                break;
            default:
                current = songList.get(0);
                next = songList.get(1);
        }
        updateSongInfo(current,next);
    }

    /**
     * Updates current and next song information
     * @param currentSong
     * @param nextSong
     */
    public void updateSongInfo(Song currentSong, Song nextSong)
    {
        if(currentSong != null)
        {
            currentTitleLabel.setText(currentSong.getTitle());
            currentArtistLabel.setText(currentSong.getArtist());
            currentAlbumLabel.setText(currentSong.getAlbum());
        }
        else
        {
            currentTitleLabel.setText("");
            currentArtistLabel.setText("");
            currentAlbumLabel.setText("");
        }

        if(nextSong != null)
        {
            nextTitleLabel.setText(nextSong.getTitle());
            nextArtistLabel.setText(nextSong.getArtist());
            nextAlbumLabel.setText(nextSong.getAlbum());
        }
        else
        {
            nextTitleLabel.setText("");
            nextArtistLabel.setText("");
            nextAlbumLabel.setText("");
        }
    }
}
