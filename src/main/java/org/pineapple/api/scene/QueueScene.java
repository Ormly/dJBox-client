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
import org.pineapple.api.controller.QueueController;

import javax.tools.Tool;

public class QueueScene implements SceneMaker {
    private Stage stage;

    public QueueScene(Stage stage){this.stage=stage;}

    @Override
    public Scene getScene(){
        // Uses controller for button handling
        QueueController controller = new QueueController(stage);

        // Search bar
        TextField textField = new TextField();
        textField.setPromptText("search");

        // Lists songs in the queue
        TableView tableView = new TableView();
        TableColumn<String, String> column1 = new TableColumn<>("Song");
        column1.setCellValueFactory(new PropertyValueFactory<>("song"));
        TableColumn<String, String> column2 = new TableColumn<>("Artist");
        column2.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableColumn<String, String> column3 = new TableColumn<>("Album");
        column3.setCellValueFactory(new PropertyValueFactory<>("album"));
        tableView.getColumns().addAll(column1,column2,column3);
        tableView.setPlaceholder(new Label("No songs are in the queue please add a song"));

        // Search bar on top of song list
        VBox vBox1 = new VBox(textField,tableView);

        // Menu options in top right Library and logout
        Tooltip tooltip1 = new Tooltip("Library");
        Image image1 = new Image("PlaceHolder.png");
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(25);
        imageView1.setFitHeight(25);
        imageView1.setPickOnBounds(true);
        Tooltip.install(imageView1,tooltip1);
        imageView1.setOnMouseClicked(e -> controller.libraryButtonHandle());

        Tooltip tooltip2 = new Tooltip("Log out");
        Image image2 = new Image("PlaceHolder.png");
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(25);
        imageView2.setFitHeight(25);
        imageView2.setPickOnBounds(true);
        Tooltip.install(imageView2,tooltip2);
        imageView2.setOnMouseClicked(e -> controller.logoutButtonHandle());

        // Menu options are next to each other
        HBox hBox1 = new HBox(20);
        hBox1.getChildren().addAll(imageView1,imageView2);
        hBox1.setAlignment(Pos.CENTER_RIGHT);

        //Album art of currently playing song
        Image image3 = new Image("PlaceHolder.png");
        ImageView imageView3 = new ImageView((image3));
        imageView3.setFitHeight(100);
        imageView3.setFitWidth(100);

        // Song information of currently playing song
        Label label1 = new Label("Thriller");
        Label label2 = new Label("Michael Jackson");
        Label label3 = new Label("Thriller");

        // Album art, on top of song information
        VBox vBox2 = new VBox(10);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(imageView3,label1,label2,label3);

        // Right arrow
        Image image4 = new Image("PlaceHolder.png");
        ImageView imageView4 = new ImageView(image4);
        imageView4.setFitWidth(25);
        imageView4.setFitHeight(25);

        // Album art for next song
        Image image5 = new Image("PlaceHolder.png");
        ImageView imageView5 = new ImageView(image5);
        imageView5.setFitWidth(50);
        imageView5.setFitHeight(50);

        // Song information for next song
        Label label4 = new Label("Heart of Glass");
        Label label5 = new Label("Blondie");
        Label label6 = new Label("Parallel Lines");

        // Stacks album art on top of song information for next song
        VBox vBox3 = new VBox(10);
        vBox3.setAlignment(Pos.CENTER);
        vBox3.getChildren().addAll(imageView5,label4,label5,label6);

        // from left to right currently playing song, right arrow, next song
        HBox hBox2 = new HBox(50);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(vBox2,imageView4,vBox3);

        // Top is for menu options, center is for song information
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox1);
        borderPane.setCenter(hBox2);

        // Main element search bar on top of song list on left, menu options and song information on right
        HBox root = new HBox(vBox1,borderPane);
        root.setSpacing(20);
        root.setPadding(new Insets(10));

        // left and right sides have equal width
        borderPane.prefWidthProperty().bind(root.widthProperty());
        vBox1.prefWidthProperty().bind(root.widthProperty());

        Scene scene = new Scene(root,800,600);
        return scene;
    }
}
