package org.pineapple.core;

import org.pineapple.backend.ServerController;
import org.pineapple.backend.interfaces.ServerControllerService;
import org.pineapple.core.interfaces.IMediaList;

public class JukeBoxClient
{
    private ServerControllerService serverController;

    private IMediaList library;
    private IMediaList queue;
    private UserData userData;

    public JukeBoxClient()
    {
        //TESTING
        serverController = new ServerController();
        String testUser = "ioncicala";
        String testPassword = "1234";
        userData = new UserData(testUser,doAuthentication(testUser,testPassword));
    }

    public String doAuthentication(String userEmail, String userPassword)
    {
        //TESTING
        String securityToken = serverController.authenticate(userEmail,userPassword);
        return securityToken;
    }

    //TESTING
    public String getTokenTest()
    {
        return userData.getSecurityToken();
    }

    //TODO: functions serving the UI
}
