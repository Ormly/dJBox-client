package org.pineapple.ui.controller;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.core.JukeBoxIPNamePair;
import org.pineapple.core.ResponseState;
import org.pineapple.core.Song;
import org.pineapple.ui.scene.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Controls and creates scenes.
 */
public class Controller {

    private Stage stage;
    private Stage dialog;
    private JukeBoxClient jukeBoxClient;
    private UserIPConnectScene userIPConnectScene;
    private UserLoginScene userLoginScene;
    private RegistrationScene registrationScene;
    private QueueScene queueScene;
    private LibraryScene libraryScene;
    private NewEditIPScene newEditIPScene;

    /**
     * Constructor creates and puts scenes into Map container.
     * @param stage window
     * @param jukeBoxClient client
     */
    public Controller(Stage stage, JukeBoxClient jukeBoxClient)
    {
        this.stage = stage;
        this.jukeBoxClient = jukeBoxClient;
        userIPConnectScene = new UserIPConnectScene(stage,this);
        userLoginScene = new UserLoginScene(stage,this);
        registrationScene = new RegistrationScene(dialog,this);
        queueScene = new QueueScene(stage,this);
        libraryScene = new LibraryScene(dialog,this);
        newEditIPScene = new NewEditIPScene(dialog, this);
    }

    /**
     * Returns the Initial Scene to connect to an IP.
     * @return userIPScene
     */
    public UserIPConnectScene getInitialScene() { return userIPConnectScene; }

    /**
     * Adds song to queue.
     */
    public void addToQueueButtonLibrary()
    {
        int id = libraryScene.getSongObservableList();
        if(id > 0)
        {
            jukeBoxClient.addSongToQueue(id);
            queueScene.updateSongObservableList(getQueueList());
            queueScene.setSongPlaying(true);
        }
    }

    /**
     * Logs user out and changes scene to login.
     */
    public void logoutButtonQueue()
    {
        ResponseState responseState = jukeBoxClient.doLogout();
        switch(responseState)
        {
            case SUCCESS:
                stage.setScene(userLoginScene);
                stage.setTitle("dJBox - Login");
                queueScene.stopQueueTimeLine();
                queueScene.stopSongPlayingTimeline();
                break;
            case AUTHFAIL:
                break;
            case CANTREACH:
                break;
        }
    }

    /**
     * Opens up a new window containing song library.
     * Main window can't be used until library is closed.
     */
    public void libraryButtonQueue()
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
        libraryScene.updateSongObservableList(songList);
        dialog.setScene(libraryScene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        dialog.setTitle("dJBox - Library");
        dialog.getIcons().add(new Image("Pineapple_logo.png"));
        dialog.show();
    }

    /**
     * Returns List of songs in the queue if it is able to otherwise an empty list.
     * @return songList
     */
    public List<Song> getQueueList()
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

    /**
     * Creates popup to enter new IP address.
     */
    public void newIPButtonUserIPConnect()
    {
        dialog = new Stage();
        newEditIPScene.setStatus(true);
        dialog.setScene(newEditIPScene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        dialog.setTitle("dJBox - New IP");
        dialog.getIcons().add(new Image("Pineapple_logo.png"));
        dialog.show();
    }

    /**
     * Creates popup to edit IP address.
     */
    public void editIPButtonUserIPConnect()
    {
        try
        {
            String name = userIPConnectScene.getKeyFromTableSelection();
            String ip = userIPConnectScene.getIPFromTableSelection();
            newEditIPScene.setIpTextField(ip);
            newEditIPScene.setNameTextField(name);
            dialog = new Stage();
            newEditIPScene.setStatus(false);
            dialog.setScene(newEditIPScene);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(stage);
            dialog.setTitle("dJBox - Edit IP");
            dialog.getIcons().add(new Image("Pineapple_logo.png"));
            dialog.show();
        }
        catch(NullPointerException npe)
        {
            System.out.println("Nothing Selected");
        }
    }

    /**
     * Adds ip to hashmap and displays.
     *
     * @param name obtained from textfield
     * @param ip obtained from textfield
     */
    public void addIPButtonNewEditIP(String name, String ip)
    {
        userIPConnectScene.putHashMap(name, ip);
        userIPConnectScene.updateObservableList();
        jukeBoxClient.addIPNamePair(name, ip);
        dialog.close();
    }

    /**
     * Deletes currently selected ip and adds new ip.
     *
     * @param name obtained from textfield
     * @param ip obtained from textfield
     */
    public void editIPButtonHandleNewEditIPScene(String name, String ip)
    {
        String key = userIPConnectScene.getKeyFromTableSelection();
        userIPConnectScene.removeHashMap(key);
        jukeBoxClient.deleteIPNamePair(key);
        userIPConnectScene.putHashMap(name, ip);
        jukeBoxClient.addIPNamePair(name, ip);
        userIPConnectScene.updateObservableList();
        dialog.close();
    }

    /**
     * Removes currently selected ip from table and hashmap.
     */
    public void deleteIPButtonUserIPConnect()
    {
        try
        {
            String key = userIPConnectScene.getKeyFromTableSelection();
            userIPConnectScene.removeHashMap(key);
            jukeBoxClient.deleteIPNamePair(key);
            userIPConnectScene.updateObservableList();
        }
        catch(NullPointerException npe)
        {
            System.out.println("Nothing Selected");
        }
    }

    /**
     * Connects to the server and changes scene to login.
     */
    public void connectButtonUserIPConnect()
    {
        try
        {
            String ip = userIPConnectScene.getIPFromTableSelection();
            if(ip != null)
            {
                String preText ="http://";
                String postText =":8080";
                ResponseState responseState = jukeBoxClient.doConnectViaIP(preText + ip + postText);
                switch(responseState)
                {
                    case SUCCESS:
                        stage.setScene(userLoginScene);
                        stage.setTitle("dJBox - Login");
                        break;
                    case CANTREACH:
                    case INVALIDIP:
                        break;
                }
            }
        }
        catch(NullPointerException npe)
        {
            System.out.println("Nothing Selected");
        }
    }

    /**
     * Fetches all jukebox name-ip pairs from JukeBoxClient.
     *
     * @return
     */
    public List<JukeBoxIPNamePair> getIPNamePairs()
    {
        return jukeBoxClient.fetchAllJukeBoxIPNamePairs();
    }

    /**
     * Logs user in if able to, otherwise displays an error message.
     *
     * @param user username/email
     * @param password password
     * @param response error message
     */
    public void loginButtonUserLogin(TextField user, PasswordField password, Label response)
    {
        ResponseState responseState = jukeBoxClient.doAuthentication(user.getText(), password.getText());
        switch(responseState){
            case SUCCESS:
                stage.setScene(queueScene);
                stage.setTitle("dJBox - Queue");
                user.setText("");
                password.setText("");
                response.setText("");
                queueScene.playQueueTimeLine();
                queueScene.playSongPlayingTimeline();
                queueScene.timeline10Seconds();
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

    /**
     * Opens new popup for registration,
     * clears registration fields,
     * main scene is inactive until popup is closed.
     */
    public void registerButtonUserLogin()
    {
        dialog = new Stage();
        registrationScene.clearFields();
        dialog.setScene(registrationScene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        dialog.setTitle("dJBox - Register");
        dialog.getIcons().add(new Image("Pineapple_logo.png"));
        dialog.show();
    }

    /**
     * Disconnects from server.
     */
    public void disconnectButtonUserLogin()
    {
        stage.setScene(userIPConnectScene);
    }

    /**
     * Signs up user from input checking for password confirmation match, empty fields and response state.
     *
     * @param emailTextField email
     * @param passwordField password
     * @param confirmPasswordField repeated password
     * @param response for errors
     */
    public void signUpButtonRegistration(TextField emailTextField, PasswordField passwordField, PasswordField confirmPasswordField,Label response)
    {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        //
        final Pattern VALIDEMAIL =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        boolean isEmailValid = VALIDEMAIL.matcher(email).find();


        if(!password.equals(confirmPassword))
        {
            response.setText("Passwords do not match");
            passwordField.setText("");
            confirmPasswordField.setText("");
        }else if(password.trim().isEmpty() && confirmPassword.trim().isEmpty()) {
            response.setText("Passwords can not be empty");
        }
        else if(email.trim().isEmpty() || !isEmailValid){
            response.setText("Please input a valid email");
        }
        else
        {
            ResponseState responseState = jukeBoxClient.doRegistration(email,password);
            switch(responseState)
            {
                case SUCCESS:
                    emailTextField.setText("");
                    passwordField.setText("");
                    confirmPasswordField.setText("");
                    dialog.close();
                    break;
                case INVALIDIP:
                    response.setText("Invalid IP");
                    break;
                case CANTREACH:
                    response.setText("Can't Reach");
                    break;
                case GENERALFAIL:
                    response.setText("General Fail");
                    break;
                case AUTHFAIL:
                    response.setText("Authentication Fail");
                    break;
                case SONGNOTFOUND:
                    response.setText("Song Not Found");
                    break;
            }
        }
    }

    /**
     * Gets current song.
     * Tests response state before returning a song if successful or null if unsuccessful.
     *
     * @return current playing song
     */
    public Song getCurrentSong() {
        ResponseState responseState = jukeBoxClient.updateCurrentSong();
        switch(responseState)
        {
            case SUCCESS:
                return jukeBoxClient.getCurrentSong();
            case SONGNOTFOUND:
            case NOCURRENTSONG:
            case AUTHFAIL:
            case INVALIDIP:
            case GENERALFAIL:
            case CANTREACH:
        }
        Song s = new Song();
        return s;
    }

    /**
     * Gets the time elapsed of the current song
     * @return elapsed time if successful or 0.0 if not successful
     */
    public double getCurrentSongElapsed()
    {
        ResponseState responseState = jukeBoxClient.updateCurrentSongElapsed();
        switch(responseState)
        {
            case SUCCESS:
                return jukeBoxClient.getCurrentSongElapsed();
            case CANTREACH:
            case GENERALFAIL:
            case INVALIDIP:
            case AUTHFAIL:
            case NOCURRENTSONG:
            case SONGNOTFOUND:
        }
        return 0.0;
    }
}