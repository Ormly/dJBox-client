package org.pineapple.ui.scene;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.pineapple.core.Song;
import org.pineapple.ui.controller.Controller;

import java.util.List;

public class LibraryScene extends SceneMaker {

    private TableView<Song> songTableView;
    private ObservableList<Song> songObservableList = FXCollections.observableArrayList();
    /**
     * Creates library scene
     * @param stage window
     * @param controller controls scene commands
     */
    public LibraryScene(Stage stage, Controller controller) {
        super(stage,controller,500,400);

        // Lists songs in the library
        songTableView = new TableView<>();
        TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableColumn<Song, String> albumColumn = new TableColumn<>("Album");
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        songTableView.getColumns().add(titleColumn);
        songTableView.getColumns().add(artistColumn);
        songTableView.getColumns().add(albumColumn);
        songTableView.setPlaceholder(new Label("No songs have been added to the library"));

        // Wrap songObservableList in FilteredList (Showing all data initially)
        FilteredList<Song> filteredList = new FilteredList<>(songObservableList, p -> true);

        // Search bar
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search for a song");
        searchTextField.textProperty().addListener((observable, oldValue, newValue)
                                                           -> filteredList.setPredicate(song -> {
            //Empty displays all
            if(newValue == null || newValue.isEmpty())
                return true;

            // Compare song title, album and artist with search bar
            String lowerCaseFilter = newValue.toLowerCase();
            if(song.getTitle().toLowerCase().contains(lowerCaseFilter))
                return true;
            else if(song.getArtist().toLowerCase().contains(lowerCaseFilter))
                return true;
            else return song.getAlbum().toLowerCase().contains(lowerCaseFilter);
        }));
        songTableView.setItems(filteredList);

        // Stacks search bar on top of song list
        VBox leftVBox = new VBox(searchTextField,songTableView);

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

        this.setRoot(root);
    }
    public void updateSongObservableList(List<Song> songList)
    {
        songObservableList.clear();
        songObservableList.addAll(songList);
    }

    public int getSongObservableList()
    {
        if(songTableView.getSelectionModel().getSelectedItem() != null)
            return songTableView.getSelectionModel().getSelectedItem().getId();
        else
            return -1;
    }
}
