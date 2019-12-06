package org.pineapple.core;

import org.pineapple.backend.AuthenticationFailedException;
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
        serverController = new ServerController();

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

            // For testing get library
            getLibraryResponseState();
            doGetLibrary();

        } catch(IOException io)
        {
        } catch(InterruptedException ie)
        {
            //TODO: think about this bullshit
            System.out.println("InterruptedException");
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
        List<Song> songs = queue.getAllMedia();

        for(Song song : songs)
        {
            System.out.println(song.getTitle());
        }

        return queue.getAllMedia();
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
            System.out.println("InterruptedException in getLibraryresponseState");
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
            System.out.println("InterruptedException");
        } catch(AuthenticationFailedException af)
        {
            return ResponseState.AUTHFAIL;
        }

        return ResponseState.SUCCESS;
    }
}


