package org.pineapple.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.core.ResponseState;
import org.pineapple.core.Song;
import org.pineapple.ui.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class QueueController extends Controller {

    public QueueController(Stage stage, JukeBoxClient jukeBoxClient) {
        super(stage, jukeBoxClient);
    }

    //Button handlers
    public void logoutButtonHandle() {
        stage.setScene(Main.getScenes().get(Scene.USERLOGINSCENE));
        stage.setTitle("dJBox - Login");
    }

    public void libraryButtonHandle() {
        Stage dialog = new Stage();
        dialog.setScene(Main.getScenes().get(Scene.LIBRARYSCENE));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        dialog.setTitle("dJBox - Library");
        dialog.getIcons().add(new Image("ananas_color.png"));
        dialog.show();
    }

    public List<Song> doGetQueue() {
        ResponseState responseState = jukeBoxClient.getQueueResponseState();
        List<Song> songList = new ArrayList<>();
        switch(responseState) {
            case SUCCESS:
                System.out.println("doGetQueue SUCCESS");
                songList = jukeBoxClient.doGetQueue();
                break;
            case AUTHFAIL:
                System.out.println("doGetQueue AUTHFAIL");
                break;
            case FATAL:
                System.out.println("doGetQueue FATAL");
                break;
        }
        return songList;
    }
}
