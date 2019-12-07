package org.pineapple.ui.controller;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.core.ResponseState;
import org.pineapple.core.Song;
import org.pineapple.ui.scene.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Controls and creates scenes
 */
public class Controller {

    private Stage stage;
    private JukeBoxClient jukeBoxClient;
    private Map<SceneEnum, Scene> scenes = new HashMap<>();

    /**
     * Constructor creates and puts scenes into Map container
     * @param stage window
     * @param jukeBoxClient client
     */
    public Controller(Stage stage, JukeBoxClient jukeBoxClient) {
        this.stage = stage;
        this.jukeBoxClient = jukeBoxClient;
        scenes.put(SceneEnum.USERIPCONNECTSCENE, new UserIPConnectScene(stage, this));
        scenes.put(SceneEnum.USERLOGINSCENE, new UserLoginScene(stage, this));
        scenes.put(SceneEnum.QUEUESCENE, new QueueScene(stage, this));
        scenes.put(SceneEnum.LIBRARYSCENE, new LibraryScene(stage, this));
    }

    /**
     * Gets scenes from Map container
     * @return scenes
     */
    public Map<SceneEnum, Scene> getScenes() {return scenes; }

    //TODO: Finish method
    /**
     * Adds song to queue
     */
    public void addToQueueButtonHandle() {
        System.out.println("Song added to queue");
    }

    /**
     * Logs user out and changes scene to login
     */
    public void logoutButtonHandle() {
        ResponseState responseState = jukeBoxClient.doLogout();
        switch(responseState)
        {
            case SUCCESS:
                stage.setScene(getScenes().get(SceneEnum.USERLOGINSCENE));
                stage.setTitle("dJBox - Login");
                break;
            case AUTHFAIL:
                break;
            case FATAL:
                break;
        }
    }

    /**
     * Opens up a new window containing song library
     * Main window can't be used until library is closed
     */
    public void libraryButtonHandle() {
        Stage dialog = new Stage();
        dialog.setScene(getScenes().get(SceneEnum.LIBRARYSCENE));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        dialog.setTitle("dJBox - Library");
        dialog.getIcons().add(new Image("ananas_color.png"));
        dialog.show();
    }

    /**
     * Returns List of songs in the queue if it is able to otherwise an empty list
     * @return songList
     */
    public List<Song> doGetQueue() {
        ResponseState responseState = jukeBoxClient.getQueueResponseState();
        List<Song> songList = new ArrayList<>();
        switch(responseState) {
            case SUCCESS:
                songList = jukeBoxClient.doGetQueue();
                break;
            case AUTHFAIL:
                break;
            case FATAL:
                break;
        }
        return songList;
    }

    //TODO: Finish method
    /**
     * Connects to the server and changes scene to login
     */
    public void connectButtonHandle() {
        stage.setScene(getScenes().get(SceneEnum.USERLOGINSCENE));
        stage.setTitle("dJBox - Login");
    }

    /**
     * Logs user in if able to, otherwise displays an error message
     * @param user username/email
     * @param password password
     * @param response error message
     */
    public void loginButtonHandle(TextField user, PasswordField password, Label response) {
        ResponseState responseState = jukeBoxClient.doAuthentication(user.getText(), password.getText());
        switch(responseState){
            case SUCCESS:
                stage.setScene(getScenes().get(SceneEnum.QUEUESCENE));
                stage.setTitle("dJBox - Queue");
                user.setText("");
                password.setText("");
                response.setText("");
                break;
            case AUTHFAIL:
                response.setText("AUTHFAIL error");
                password.setText("");
                break;
            case FATAL:
                response.setText("FATAL error");
                break;
        }
    }
}