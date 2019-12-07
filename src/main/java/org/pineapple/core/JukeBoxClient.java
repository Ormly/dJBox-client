package org.pineapple.core;

import org.pineapple.backend.AuthenticationFailedException;
import org.pineapple.backend.HTTPControllerJavaNet;
import org.pineapple.backend.ServerController;
import org.pineapple.backend.interfaces.ServerControllerService;
import org.pineapple.core.interfaces.IMediaList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO: proper exception handling, prepare ui functionality
public class JukeBoxClient
{
    private final ServerControllerService serverController;
    private static JukeBoxClient jukeBoxClientInstance;

    private IMediaList library;
    private IMediaList queue;
    private UserData userData;

    //private boolean validResponse;

    private JukeBoxClient()
    {
        queue = new SongList();
        library = new SongList();
        //validResponse = false;

        //TESTING
        serverController = new ServerController(new HTTPControllerJavaNet());

        userData = new UserData();
    }

    public static JukeBoxClient getJukeBoxClientInstance()
    {
        if(jukeBoxClientInstance == null)
            jukeBoxClientInstance = new JukeBoxClient();

        return jukeBoxClientInstance;
    }

    public ResponseState doAuthentication(String userEmail, String userPassword)
    {
        try
        {

            userData.setSecurityToken(serverController.authenticateUser(userEmail, userPassword));
            userData.setEmailAddress(userEmail);
        } catch(IOException io)
        {
        } catch(InterruptedException ie)
        {
            //...
        } catch(AuthenticationFailedException af)
        {
            return ResponseState.AUTHFAIL;
        }

        return ResponseState.SUCCESS;
    }

    public ResponseState getQueueResponseState()
    {
        try
        {
            queue.setSongList(serverController.getServerQueueWithToken(userData.getSecurityToken()));

        } catch(IOException io)
        {
            return ResponseState.FATAL;
        } catch(InterruptedException ie)
        {
            //???
        } catch(AuthenticationFailedException af)
        {

            return ResponseState.AUTHFAIL;
        }

        return ResponseState.SUCCESS;
    }

    //TODO: think about returning Optional instead
    public List<Song> doGetQueue()
    {
        //TODO: proofing
        return queue.getAllMedia();
    }

    public ResponseState addSongToQueue(int songID)
    {
        try
        {
            serverController.addSongToServerQueue(songID, userData.getSecurityToken());
        } catch(IOException io)
        {
            return ResponseState.FATAL;
        } catch(InterruptedException ie)
        {
            //...
        } catch(AuthenticationFailedException af)
        {
            return ResponseState.AUTHFAIL;
        }

        return ResponseState.SUCCESS;
    }

    public ResponseState getLibraryResponseState()
    {
        try
        {
            library.setSongList(serverController.getServerLibraryWithToken(userData.getSecurityToken()));
        } catch(IOException io)
        {
            return ResponseState.FATAL;
        } catch(InterruptedException ie)
        {
            //...
        } catch(AuthenticationFailedException af)
        {
            return ResponseState.AUTHFAIL;
        }

        return ResponseState.SUCCESS;
    }

    public List<Song> doGetLibrary()
    {

        return library.getAllMedia();
    }

    public ResponseState doLogout()
    {
        try
        {
            serverController.logoutUser(userData.getSecurityToken());
        } catch(IOException io)
        {
            return ResponseState.FATAL;
        } catch(InterruptedException ie)
        {
            //...
        } catch(AuthenticationFailedException af)
        {
            return ResponseState.AUTHFAIL;
        }

        return ResponseState.SUCCESS;
    }

    public void setJukeBoxIP(String ipAddress)
    {
        serverController.setRequestURI(ipAddress);
    }
}


