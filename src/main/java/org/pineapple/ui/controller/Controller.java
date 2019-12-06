package org.pineapple.ui.controller;

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
import org.pineapple.ui.scene.SceneEnum;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private Stage stage;
    private JukeBoxClient jukeBoxClient;

    public Controller(Stage stage, JukeBoxClient jukeBoxClient) {
        this.stage = stage;
        this.jukeBoxClient = jukeBoxClient;
    }

    public void addToQueueButtonHandle() {
        System.out.println("Song added to queue");
    }

    public void logoutButtonHandle() {
        //TODO: What to do with responsestate
        jukeBoxClient.doLogout();
        stage.setScene(Main.getScenes().get(SceneEnum.USERLOGINSCENE));
        stage.setTitle("dJBox - Login");
    }

    public void libraryButtonHandle() {
        Stage dialog = new Stage();
        dialog.setScene(Main.getScenes().get(SceneEnum.LIBRARYSCENE));
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

    public void connectButtonHandle() {
        stage.setScene(Main.getScenes().get(SceneEnum.USERLOGINSCENE));
        stage.setTitle("dJBox - Login");
    }

    public void loginButtonHandle(TextField user, PasswordField password, Label response) {
        ResponseState responseState = jukeBoxClient.doAuthentication(user.getText(), password.getText());
        switch(responseState){
            case SUCCESS:
                stage.setScene(Main.getScenes().get(SceneEnum.QUEUESCENE));
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
