package org.pineapple.ui.controller;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.pineapple.Main;
import org.pineapple.core.JukeBoxClient;
import org.pineapple.core.ResponseState;
import org.pineapple.ui.scene.Scene;

public class UserLoginController extends Controller {

    public UserLoginController(Stage stage, JukeBoxClient jukeBoxClient) {
        super(stage, jukeBoxClient);
    }

    //Button handlers
    public void loginButtonHandle(TextField user, PasswordField password, Label response) {
        ResponseState responseState = jukeBoxClient.doAuthentication(user.getText(), password.getText());
        switch(responseState){
            case SUCCESS:
                stage.setScene(Main.getScenes().get(Scene.QUEUESCENE));
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
