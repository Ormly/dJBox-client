package org.pineapple.ui.controller;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.core.ResponseState;
import org.pineapple.core.Song;
import org.pineapple.ui.scene.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controls and creates scenes
 */
public class Controller {

    private Stage stage;
    private JukeBoxClient jukeBoxClient;
    private UserIPConnectScene userIPConnectScene;
    private UserLoginScene userLoginScene;
    private QueueScene queueScene;
    private LibraryScene libraryScene;

    /**
     * Constructor creates and puts scenes into Map container
     * @param stage window
     * @param jukeBoxClient client
     */
    public Controller(Stage stage, JukeBoxClient jukeBoxClient) {
        this.stage = stage;
        this.jukeBoxClient = jukeBoxClient;
        userIPConnectScene = new UserIPConnectScene(stage,this);
        userLoginScene = new UserLoginScene(stage,this);
        queueScene = new QueueScene(stage,this);
        libraryScene = new LibraryScene(stage,this);
    }

    public UserIPConnectScene getUserIPConnectScene() { return userIPConnectScene; }

    public UserLoginScene getUserLoginScene() { return userLoginScene; }

    public QueueScene getQueueScene() { return queueScene; }

    public LibraryScene getLibraryScene() { return libraryScene; }

    //TODO: Finish method
    /**
     * Adds song to queue
     */
    public void addToQueueButtonHandle()
    {
        int id = libraryScene.getSongObservableList();
        if(id > 0)
        {
            jukeBoxClient.addSongToQueue(id);
            queueScene.updateSongObservableList(doGetQueue());
        }
    }

    /**
     * Logs user out and changes scene to login
     */
    public void logoutButtonHandle() {
        ResponseState responseState = jukeBoxClient.doLogout();
        switch(responseState)
        {
            case SUCCESS:
                stage.setScene(getUserLoginScene());
                stage.setTitle("dJBox - Login");
                getQueueScene().stopTimeLine();
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
        ResponseState responseState = jukeBoxClient.getLibraryResponseState();
        List<Song> songList = new ArrayList<>();
        switch(responseState)
        {
            case SUCCESS:
                songList = jukeBoxClient.doGetLibrary();
                break;
            case AUTHFAIL:
                break;
            case FATAL:
                break;
        }
        Stage dialog = new Stage();
        getLibraryScene().updateSongObservableList(songList);
        dialog.setScene(getLibraryScene());
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
        stage.setScene(getUserLoginScene());
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
                stage.setScene(getQueueScene());
                stage.setTitle("dJBox - Queue");
                user.setText("");
                password.setText("");
                response.setText("");
                getQueueScene().playTimeLine();
                getQueueScene().updateSongObservableList(doGetQueue());
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