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
import javafx.scene.effect.Effect;
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

    private Timeline queueTimeline;
    private Timeline songPlayingTimeline;
    private ObservableList<Song> songObservableList = FXCollections.observableArrayList();
    private Label currentTitleLabel;
    private Label currentArtistLabel;
    private Label currentAlbumLabel;
    private Label nextTitleLabel;
    private Label nextArtistLabel;
    private Label nextAlbumLabel;
    private ImageView currentAlbumImageView;
    private ImageView nextAlbumImageView;
    private ImageView rightArrowImageView;
    private Image placeHolderImage;
    private Label timeElapsedLabel;
    private Label songDurationLabel;

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
        queueTimeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            updateSongObservableList(controller.getQueueList());
            System.out.println(controller.getCurrentSongElapsed() + " " + controller.getCurrentSong().getDuration());
        }));
        queueTimeline.setCycleCount(Timeline.INDEFINITE);

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
        Image libraryImage = new Image("book-music.png");
        ImageView libraryImageView = new ImageView(libraryImage);
        libraryImageView.setFitWidth(25);
        libraryImageView.setFitHeight(25);
        libraryImageView.setPickOnBounds(true);
        Tooltip.install(libraryImageView,libraryTooltip);
        libraryImageView.setOnMouseClicked(e -> controller.libraryButtonQueue());

        // Logout button
        Tooltip logoutTooltip = new Tooltip("Log out");
        Image logoutImage = new Image("logout.png");
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
        placeHolderImage = new Image("PlaceHolder.png");
        currentAlbumImageView = new ImageView((placeHolderImage));
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
        Image rightArrowImage = new Image("right_arrow.png");
        rightArrowImageView = new ImageView(rightArrowImage);
        rightArrowImageView.setFitWidth(25);
        rightArrowImageView.setFitHeight(25);

        // Album art for next song
        nextAlbumImageView = new ImageView(placeHolderImage);
        nextAlbumImageView.setFitWidth(50);
        nextAlbumImageView.setFitHeight(50);

        // Song information for next song
        nextTitleLabel = new Label();
        nextArtistLabel = new Label();
        nextAlbumLabel = new Label();

        // Song duration labels
        timeElapsedLabel = new Label("00:00");
        songDurationLabel = new Label("00:00");

        // Song Progress Bar
        ProgressBar songProgressBar = new ProgressBar(0);
        songPlayingTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                System.out.println(controller.getCurrentSongElapsed());
        }));
//        songPlayingTimeline.play();
        songPlayingTimeline.setCycleCount(Timeline.INDEFINITE);

        // Stacks album art on top of song information for next song
        VBox nextSongVBox = new VBox(10,nextAlbumImageView,nextTitleLabel,nextArtistLabel,nextAlbumLabel);
        nextSongVBox.setAlignment(Pos.CENTER);

        // from left to right currently playing song, right arrow, next song
        HBox songQueueHBox = new HBox(50,currentSongVBox,rightArrowImageView,nextSongVBox);
        songQueueHBox.setAlignment(Pos.CENTER);

        HBox songProgressHBox = new HBox(10,timeElapsedLabel,songProgressBar,songDurationLabel);
        songProgressHBox.setAlignment(Pos.CENTER);

        VBox songsProgressVBox = new VBox(50,songQueueHBox,songProgressHBox);
        songsProgressVBox.setAlignment(Pos.CENTER);

        // Top is for menu options, center is for song information
        BorderPane rightBorderPane = new BorderPane();
        rightBorderPane.setTop(rightTopBorderHBox);
        rightBorderPane.setCenter(songsProgressVBox);

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
    public void playQueueTimeLine() { queueTimeline.play(); }
    public void stopQueueTimeLine() { queueTimeline.stop(); }

    /**
     * Updates the list and updates current and next song
     * @param songList
     */
    public void updateSongObservableList(List<Song> songList)
    {
        songObservableList.clear();
        songObservableList.addAll(songList);
        Song current = controller.getCurrentSong();
        Song next = null;
        if(!songObservableList.isEmpty())
            next = songObservableList.get(0);

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
//            updatePlayingSongInfo(currentSong);
            try
            {
                currentAlbumImageView.setImage(new Image(currentSong.getCoverArtURL()));
            }
            catch(Exception e)
            {
                currentAlbumImageView.setImage(placeHolderImage);
            }
            currentAlbumImageView.setVisible(true);
        }
        else
        {
            currentTitleLabel.setText("");
            currentArtistLabel.setText("");
            currentAlbumLabel.setText("");
            currentAlbumImageView.setVisible(false);
        }

        if(nextSong != null)
        {
            nextTitleLabel.setText(nextSong.getTitle());
            nextArtistLabel.setText(nextSong.getArtist());
            nextAlbumLabel.setText(nextSong.getAlbum());
            try
            {
                nextAlbumImageView.setImage(new Image(nextSong.getCoverArtURL()));
            }
            catch(Exception e)
            {
                nextAlbumImageView.setImage(placeHolderImage);
            }
            nextAlbumImageView.setVisible(true);
            rightArrowImageView.setVisible(true);
        }
        else
        {
            nextTitleLabel.setText("");
            nextArtistLabel.setText("");
            nextAlbumLabel.setText("");
            nextAlbumImageView.setVisible(false);
            rightArrowImageView.setVisible(false);
        }
    }

    public String getTimeInMMSS(double duration)
    {
        int minutes = (int)duration / 60;
        int seconds = (int)duration % 60;

        return String.format("%02d:%02d",minutes,seconds);
    }

    public void updatePlayingSongInfo(Song song)
    {
        songDurationLabel.setText(getTimeInMMSS(song.getDuration()));
        timeElapsedLabel.setText(getTimeInMMSS(controller.getCurrentSongElapsed()));
    }
}
