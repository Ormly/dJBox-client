package org.pineapple.core;

import org.pineapple.backend.AuthenticationFailedException;
import org.pineapple.backend.ServerController;
import org.pineapple.backend.interfaces.ServerControllerService;
import org.pineapple.core.interfaces.IMediaList;

import java.io.IOException;
import java.util.List;

//TODO: proper exception handling, prepare ui functionality
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
        String testUser = "testperson@gmail.com";
        String testPassword = "password";
        userData = new UserData(testUser, doAuthentication(testUser, testPassword));
    }

    public String doAuthentication(String userEmail, String userPassword)
    {
        StringBuilder securityToken = new StringBuilder();

        try
        {
            securityToken.append(serverController.authenticateUser(userEmail, userPassword));
        } catch(IOException io)
        {

        } catch(InterruptedException ie)
        {

        } catch(AuthenticationFailedException af)
        {

        }

        return securityToken.toString();
    }

    public List<Song> doGetQueue()
    {
        queue = new SongList();

        try
        {
            queue.setSongList(serverController.getServerQueueWithToken(userData.getSecurityToken()));
        } catch(IOException io)
        {

        } catch(InterruptedException ie)
        {

        } catch(AuthenticationFailedException af)
        {

        }

        return queue.getAllMedia();
    }

    public UserData getUserData()
    {
        return userData;
    }
}
