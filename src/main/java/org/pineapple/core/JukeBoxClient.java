package org.pineapple.core;

import org.pineapple.backend.ServerController;
import org.pineapple.backend.interfaces.ServerControllerService;
import org.pineapple.core.interfaces.IMediaList;

import java.util.List;

public class JukeBoxClient
{
    private ServerControllerService serverController;

    private IMediaList library;
    private IMediaList queue;
//    private SongList library;
//    private SongList queue;
    private UserData userData;

    public UserData getUserData()
    {
        return userData;
    }

    public JukeBoxClient()
    {
        //TESTING
        serverController = new ServerController();
        String testUser = "testperson@gmail.com";
        String testPassword = "password";
        userData = new UserData(testUser, doAuthentication(testUser, testPassword));
    }

    public String doAuthentication(String userEmail, String userPassword)
    {
        //TESTING
        String securityToken = serverController.authenticateUser(userEmail, userPassword);
        return securityToken;
    }

    //TESTING
    public String getTokenTest()
    {
        return userData.getSecurityToken();
    }

    //TODO: functions serving the UI

    public List<Song> doGetQueue()
    {
        queue = new SongList();
        queue.setSongList(serverController.getServerQueueWithToken(userData.getSecurityToken()));
    return queue.getAllMedia();
    }


}
