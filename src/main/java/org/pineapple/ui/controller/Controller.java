package org.pineapple.ui.controller;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pineapple.core.JukeBox;
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
    private Stage dialog;
    private JukeBoxClient jukeBoxClient;
    private UserIPConnectScene userIPConnectScene;
    private UserLoginScene userLoginScene;
    private QueueScene queueScene;
    private LibraryScene libraryScene;
    private NewIPScene newIPScene;
    private EditIPScene editIPScene;

    /**
     * Constructor creates and puts scenes into Map container
     * @param stage window
     * @param jukeBoxClient client
     */
    public Controller(Stage stage, JukeBoxClient jukeBoxClient)
    {
        this.stage = stage;
        this.jukeBoxClient = jukeBoxClient;
        userIPConnectScene = new UserIPConnectScene(stage,this);
        userLoginScene = new UserLoginScene(stage,this);
        queueScene = new QueueScene(stage,this);
        libraryScene = new LibraryScene(dialog,this);
        newIPScene = new NewIPScene(dialog,this);
        editIPScene = new EditIPScene(dialog,this);
    }

    public UserIPConnectScene getUserIPConnectScene() { return userIPConnectScene; }
    public UserLoginScene getUserLoginScene() { return userLoginScene; }
    public QueueScene getQueueScene() { return queueScene; }
    public LibraryScene getLibraryScene() { return libraryScene; }
    public NewIPScene getNewIPScene() { return newIPScene; }
    public EditIPScene getEditIPScene() { return editIPScene; }

    /**
     * Adds song to queue
     */
    public void addToQueueButtonHandleLibraryScene()
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
    public void logoutButtonHandleQueueScene()
    {
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
            case CANTREACH:
                break;
        }
    }

    /**
     * Opens up a new window containing song library
     * Main window can't be used until library is closed
     */
    public void libraryButtonHandleQueueScene()
    {
        ResponseState responseState = jukeBoxClient.getLibraryResponseState();
        List<Song> songList = new ArrayList<>();
        switch(responseState)
        {
            case SUCCESS:
                songList = jukeBoxClient.doGetLibrary();
                break;
            case AUTHFAIL:
                break;
            case CANTREACH:
                break;
        }
        dialog = new Stage();
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
    public List<Song> doGetQueue()
    {
        ResponseState responseState = jukeBoxClient.getQueueResponseState();
        List<Song> songList = new ArrayList<>();
        switch(responseState) {
            case SUCCESS:
                songList = jukeBoxClient.doGetQueue();

                break;
            case AUTHFAIL:
                break;
            case CANTREACH:
                break;
        }
        return songList;
    }

    public void newIPButtonHandleUserIPConnectScene()
    {
        dialog = new Stage();
        dialog.setScene(getNewIPScene());
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        dialog.setTitle("dJBox - New IP");
        dialog.getIcons().add(new Image("ananas_color.png"));
        dialog.show();
    }

    public void editIPButtonHandleUserIPConnectScene()
    {
        JukeBox jukeBox = getUserIPConnectScene().getJukeBoxTableView().getSelectionModel().getSelectedItem();
        if(jukeBox != null)
        {
            getEditIPScene().setIpTextField(jukeBox.getIpAddress());
            getEditIPScene().setNameTextField(jukeBox.getName());
            dialog = new Stage();
            dialog.setScene(getEditIPScene());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(stage);
            dialog.setTitle("dJBox - Edit IP");
            dialog.getIcons().add(new Image("ananas_color.png"));
            dialog.show();
        }
    }

    public void addIPButtonHandleNewIPScene(String name, String ip)
    {
        getUserIPConnectScene().getJukeBoxObservableList().add(new JukeBox(name, ip));
        dialog.close();
    }

    public void editIPButtonHandleEditIPScene(String name, String ip)
    {
        getUserIPConnectScene().getJukeBoxObservableList().set(
                getUserIPConnectScene().getJukeBoxTableView().getSelectionModel().getSelectedIndex(),
                new JukeBox(name, ip)
        );
        dialog.close();
    }

    public void deleteIPButtonHandleUserIPConnectScene(JukeBox jukeBox)
    {
        getUserIPConnectScene().getJukeBoxObservableList().remove(jukeBox);
    }

    /**
     * Connects to the server and changes scene to login
     */
    public void connectButtonHandleUserIPConnectScene(JukeBox jukeBox)
    {
        if(jukeBox != null)
        {
            String preText ="http://";
            String postText =":8080";
            ResponseState responseState = jukeBoxClient.doConnectViaIP(preText + jukeBox.getIpAddress() + postText);
            switch(responseState)
            {
                case SUCCESS:
                    stage.setScene(getUserLoginScene());
                    stage.setTitle("dJBox - Login");
                    break;
                case CANTREACH:
                case WRONGSTATE:
                    break;
            }
        }
    }

    /**
     * Logs user in if able to, otherwise displays an error message
     * @param user username/email
     * @param password password
     * @param response error message
     */
    public void loginButtonHandleUserLoginScene(TextField user, PasswordField password, Label response)
    {
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
            case CANTREACH:
                response.setText("CANTREACH error");
                break;
        }
    }
}